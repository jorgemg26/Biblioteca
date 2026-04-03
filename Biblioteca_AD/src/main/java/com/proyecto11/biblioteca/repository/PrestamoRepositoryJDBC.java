package com.proyecto11.biblioteca.repository;

import com.proyecto11.biblioteca.model.Prestamo;
import java.sql.Connection;
import java.sql.SQLException;
public class PrestamoRepositoryJDBC {
    public Prestamo insert(Connection con, Prestamo p) throws SQLException {
        String sql = """ 
             INSERT INTO prestamo(usuario_id, libro_id, fecha_inicio, fecha_fin)
             VALUES (?, ?, ?, ?)
             RETURNING id
             """;
        try (var ps = con.prepareStatement(sql)) {
            ps.setLong(1, p.getUsuarioId());
            ps.setInt(2, p.getLibroId());
            ps.setObject(3, p.getFechaInicio());
            ps.setObject(4, p.getFechaFin());
            try (var rs = ps.executeQuery()) {
                rs.next();
                p.setId(rs.getLong(1));
                return p;
            }
        }
    }
    public long marcarDevuelto(Connection con, int libroId) throws SQLException {
        String sql = """
             UPDATE prestamo
             SET fecha_devolucion = CURRENT_DATE
             WHERE libro_id = ? AND fecha_devolucion IS NULL
             RETURNING id
         """;
        try (var ps = con.prepareStatement(sql)) {
            ps.setInt(1, libroId);
            try (var rs = ps.executeQuery()) {
                if (!rs.next()) throw new IllegalStateException("No hay préstamo activo para libro id=" + libroId);
                return rs.getLong("id");
            }
        }
    }
}