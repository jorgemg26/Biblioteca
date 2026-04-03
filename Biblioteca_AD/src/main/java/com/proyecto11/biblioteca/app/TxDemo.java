package com.proyecto11.biblioteca.app;

import com.proyecto11.biblioteca.repository.LibroRepositoryJDBC;
import com.proyecto11.biblioteca.service.TxLibroService;
public class TxDemo {
    public static void main(String[] args) {
        var repo = new LibroRepositoryJDBC();
        var tx = new TxLibroService(repo);
        System.out.println("=== DEMO TRANSACCION (rollback) ===");
        try {
            tx.marcarNoDisponiblePeroFallar(1);
        } catch (Exception e) {
            System.out.println("Esperado: " + e.getMessage());
        }
        System.out.println("Comprueba en pgAdmin que el libro id=1 NO se quedó como disponible=false.");
    }
}
