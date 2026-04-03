package com.proyecto11.biblioteca.service;

import com.proyecto11.biblioteca.db.Db;
import com.proyecto11.biblioteca.model.Libro;
import com.proyecto11.biblioteca.repository.LibroRepositoryJDBC;
import java.sql.Connection;

public class TxLibroService {
    private final LibroRepositoryJDBC jdbcRepo;
    public TxLibroService(LibroRepositoryJDBC jdbcRepo) {
        this.jdbcRepo = jdbcRepo;
    }
    public void marcarNoDisponiblePeroFallar(int idLibro) {
        try (Connection con = Db.getConnection()) {
            // 1) Empieza transacción
            con.setAutoCommit(false);
            try {
                // 2) Leer libro
                Libro l = jdbcRepo.findByIdOrNull(con, idLibro);
                if (l == null) throw new IllegalArgumentException("No existe libro id=" + idLibro);
                // 3) Cambiar estado en el objeto
                l.setDisponible(false);
                // 4) Guardar en BD (dentro de la transacción)
                boolean ok = jdbcRepo.update(con, l);
                if (!ok) throw new IllegalStateException("No se pudo actualizar id=" + idLibro);
                // 5) Forzar fallo
                throw new RuntimeException("Fallo forzado para demostrar rollback");
                // 6) commit (no llegamos aquí)
                //con.commit();
            } catch (Exception e) {
                // 7) rollback si algo falla
                con.rollback();
                throw e;
            } finally {
                // 8) volver a autoCommit(true)
                con.setAutoCommit(true);
            }
        } catch (Exception e) {
            // 9) envolver en RuntimeException para no obligar a throws en App
            throw new RuntimeException("TxLibroService: se hizo rollback. Motivo: " + e.getMessage(), e);
        }
    }
}
