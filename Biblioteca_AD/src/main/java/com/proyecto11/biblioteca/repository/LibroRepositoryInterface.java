package com.proyecto11.biblioteca.repository;

import com.proyecto11.biblioteca.model.Libro;
import java.util.List;
import java.util.Optional;

public interface LibroRepositoryInterface {
    List<Libro> findAll();
    Optional<Libro> findById(int id);
    List<Libro> searchByTitulo(String titulo);
    int insert(Libro libro);
    boolean update(Libro libro);
    boolean deleteById(int id);
}

