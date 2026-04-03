package com.proyecto11.biblioteca.app;

import com.proyecto11.biblioteca.model.Libro;
import com.proyecto11.biblioteca.repository.LibroRepositoryInterface;
import com.proyecto11.biblioteca.repository.LibroRepositoryJDBC;
import com.proyecto11.biblioteca.service.LibroService;

public class CapasDemo {
    public static void main(String[] args) {
        LibroRepositoryInterface repo = new LibroRepositoryJDBC();
        LibroService service = new LibroService(repo);
        System.out.println("=== LISTADO ===");
        service.listar().forEach(System.out::println);
        System.out.println("\n=== ALTA ===");
        Libro nuevo = new Libro("Clean Code", "9780132350884-X", 2008, true);
        int id = service.alta(nuevo);
        System.out.println("Creado: " + nuevo);
        System.out.println("\n=== MARCAR NO DISPONIBLE ===");
        service.marcarNoDisponible(id);
        System.out.println(service.verDetalle(id));
        System.out.println("\n=== BORRAR ===");
        service.borrar(id);
        System.out.println("Borrado OK");
    }
}
