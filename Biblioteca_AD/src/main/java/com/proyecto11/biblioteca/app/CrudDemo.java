package com.proyecto11.biblioteca.app;


import com.proyecto11.biblioteca.model.Libro;
import com.proyecto11.biblioteca.repository.LibroRepositoryJDBC;

public class CrudDemo {
    public static void main(String[] args) {
        var dao = new LibroRepositoryJDBC();
        System.out.println("=== ESTADO INICIAL ===");
        dao.findAll().forEach(System.out::println);
        System.out.println("=== INSERT ===");
        var nuevo = new Libro("Clean Code", "9780132350884-2", 2008, true);
        int id = dao.insert(nuevo);
        System.out.println("Insertado con id=" + id + " -> " + nuevo);
        System.out.println("=== FIND BY ID ===");
        dao.findById(id).ifPresentOrElse(System.out::println, () -> System.out.println("No encontrado"));
        System.out.println("=== UPDATE (marcar no disponible) ===");
        nuevo.setDisponible(false);
        boolean updated = dao.update(nuevo);
        System.out.println("updated=" + updated);
        System.out.println("Después del update: " + dao.findById(id).orElse(null));
        System.out.println("=== DELETE ===");
        boolean deleted = dao.deleteById(id);
        System.out.println("deleted=" + deleted);
        System.out.println("=== ESTADO FINAL ===");
        dao.findAll().forEach(System.out::println);
    }
}
