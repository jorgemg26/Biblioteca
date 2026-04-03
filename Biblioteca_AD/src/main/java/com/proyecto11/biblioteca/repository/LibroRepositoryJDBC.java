package com.proyecto11.biblioteca.repository;

import com.proyecto11.biblioteca.db.Db;
import com.proyecto11.biblioteca.model.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Connection;
import java.sql.SQLException;

//Clase que habla con la BD (convierte filas en objetos)
public class LibroRepositoryJDBC implements LibroRepositoryInterface {
    //Metodo helper para no repetir codigo
    private Libro mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String titulo = rs.getString("titulo");
        String isbn = rs.getString("isbn");
        Integer anio = (Integer) rs.getObject("anio");               //Admite NULL
        boolean disponible = rs.getBoolean("disponible");
        return new Libro(id, titulo, isbn, anio, disponible);
    }
    @Override
    //Metodo que devuelve todos los libros de la BD
    public List<Libro> findAll() {
        String sql = "SELECT id, titulo, isbn, anio, disponible FROM libro ORDER BY id";
        try (
                Connection con = Db.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            List<Libro> libros = new ArrayList<>();
            while (rs.next()) {
                libros.add(mapRow(rs));
            }
            return libros;
        } catch (SQLException e) {
            throw new RuntimeException("Error leyendo libros", e);
        }
    }
    @Override
    public List<Libro> searchByTitulo(String titulo) {
        String sql = "SELECT id, titulo, isbn, anio, disponible FROM libro WHERE LOWER(titulo) LIKE ? ORDER BY id";
        try (
                Connection con = Db.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, "%" + titulo.toLowerCase() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                List<Libro> libros = new ArrayList<>();
                while (rs.next()){
                    libros.add(mapRow(rs));
                }
                return libros;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Error buscando libros por titulo", e);
        }
    }
    @Override
    public Optional<Libro> findById(int id) {
        String sql = "SELECT id, titulo, isbn, anio, disponible FROM libro WHERE id = ?";
        try (
                Connection con = Db.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error buscando libro por id=" + id, e);
        }
    }
    @Override
    public int insert(Libro libro) {
        String sql = "INSERT INTO libro (titulo, isbn, anio, disponible) VALUES (?,?,?,?)";
        try (
                Connection con = Db.getConnection();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getIsbn());
            ps.setObject(3, libro.getAnio()); // admite NULL
            ps.setBoolean(4, libro.isDisponible());

            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new SQLException("INSERT inesperado. Filas afectadas: " + rows);
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    libro.setId(id);
                    return id;
                }
                throw new SQLException("No se devolvió id generado (getGeneratedKeys vacío)");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando libro", e);
        }
    }
    @Override
    public boolean update(Libro libro) {
        if (libro.getId() == null) {
            throw new IllegalArgumentException("No se puede UPDATE sin id. ¿Hiciste insert primero?");
        }
        String sql = "UPDATE libro SET titulo=?, isbn=?, anio=?, disponible=? WHERE id=?";
        try (
                Connection con = Db.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getIsbn());
            ps.setObject(3, libro.getAnio());
            ps.setBoolean(4, libro.isDisponible());
            ps.setInt(5, libro.getId());

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando libro id=" + libro.getId(), e);
        }
    }
    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM libro WHERE id=?";
        try (
                Connection con = Db.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error borrando libro id=" + id, e);
        }
    }
    // Buscar por id usando Connection compartida
    public Libro findByIdOrNull(Connection con, int id) throws SQLException {
        String sql = "SELECT id, titulo, isbn, anio, disponible FROM libro WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return mapRow(rs);  // reutiliza tu mapRow(ResultSet)
            }
        }
    }
    // Update usando Connection compartida
    public boolean update(Connection con, Libro libro) throws SQLException {
        String sql = "UPDATE libro SET titulo=?, isbn=?, anio=?, disponible=? WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getIsbn());
            if (libro.getAnio() == null) ps.setObject(3, null);
            else ps.setInt(3, libro.getAnio());
            ps.setBoolean(4, libro.isDisponible());
            ps.setInt(5, libro.getId());
            return ps.executeUpdate() == 1;
        }
    }
    public boolean isDisponible(Connection con, int libroId) throws SQLException {
        String sql = "SELECT disponible FROM libro WHERE id = ?";
        try (var ps = con.prepareStatement(sql)) {
            ps.setInt(1, libroId);
            try (var rs = ps.executeQuery()) {
                if (!rs.next()) return false;
                return rs.getBoolean("disponible");
            }
        }
    }
    // Actualizar disponibilidad
    public boolean updateDisponible(Connection con, int libroId, boolean disponible) throws SQLException {
        String sql = "UPDATE libro SET disponible = ? WHERE id = ?";
        try (var ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, disponible);
            ps.setInt(2, libroId);
            return ps.executeUpdate() == 1;
        }
    }
}