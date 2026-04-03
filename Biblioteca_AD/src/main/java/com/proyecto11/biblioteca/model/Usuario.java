package com.proyecto11.biblioteca.model;

public class Usuario {
    private Long id;
    private String nombre;
    private String email;
    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }
    public Usuario(Long id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public Long getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getEmail() {
        return email;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
