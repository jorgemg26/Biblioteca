package com.proyecto11.biblioteca.app;

import com.proyecto11.biblioteca.repository.LibroRepositoryJDBC;
import com.proyecto11.biblioteca.model.Libro;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        LibroRepositoryJDBC dao = new LibroRepositoryJDBC();
        System.out.println("=== LIBROS ===");
        List<Libro> listaLibros = dao.findAll();
        for (int i=0; i<listaLibros.size(); i++){
            System.out.println(listaLibros.get(i));
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("\n=== SEARCH BY TITULO ===");
        System.out.println("Introduce el titulo");
        String titulo = scan.nextLine().toLowerCase();
        List<Libro> resultados = dao.searchByTitulo(titulo);

        if (resultados.isEmpty()){
            System.out.println("No se encontraron libros");
        }
        else{
            for (Libro l : resultados){
                System.out.println(l);
            }
        }
    }
}

