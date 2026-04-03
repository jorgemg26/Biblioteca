package com.proyecto11.biblioteca.service;

import com.proyecto11.biblioteca.db.Db;
import com.proyecto11.biblioteca.model.Prestamo;
import com.proyecto11.biblioteca.repository.LibroRepositoryJDBC;
import com.proyecto11.biblioteca.repository.PrestamoRepositoryJDBC;
import java.sql.Connection;
import java.time.LocalDate;

public class PrestamoService {
    private final LibroRepositoryJDBC libroRepo;
    private final PrestamoRepositoryJDBC prestamoRepo;
    public PrestamoService(LibroRepositoryJDBC libroRepo, PrestamoRepositoryJDBC prestamoRepo) {
        this.libroRepo = libroRepo;
        this.prestamoRepo = prestamoRepo;
    }
    public Prestamo prestarLibro(long usuarioId, int libroId, LocalDate inicio, LocalDate fin) {
        try (Connection con = Db.getConnection()) {
            con.setAutoCommit(false);
            try {
                if (!libroRepo.isDisponible(con, libroId)) {
                    throw new IllegalStateException("El libro no está disponible (o no existe). id=" + libroId);
                }
                Prestamo p = new Prestamo(usuarioId, libroId, inicio, fin);
                Prestamo guardado = prestamoRepo.insert(con, p);
                boolean ok = libroRepo.updateDisponible(con, libroId, false);
                if (!ok) throw new IllegalStateException("No se pudo marcar como no disponible id=" + libroId);
                con.commit();
                return guardado;
            }
            catch (Exception e) {
                con.rollback();
                throw e;
            }
            finally {
                con.setAutoCommit(true);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error al prestar (rollback realizado): " + e.getMessage(), e);
        }
    }
    public long devolverLibro(int libroId) {
        try (Connection con = Db.getConnection()) {
            con.setAutoCommit(false);
            try {
                long prestamoId = prestamoRepo.marcarDevuelto(con, libroId);
                boolean ok = libroRepo.updateDisponible(con, libroId, false);
                if (!ok) throw new IllegalStateException("No se pudo marcar disponible id=" + libroId);
                con.commit();
                return prestamoId;
            }
            catch (Exception e) {
                con.rollback();
                throw e;
            }
            finally {
                con.setAutoCommit(true);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error al devolver (rollback realizado): " + e.getMessage(), e);
        }
    }
}
