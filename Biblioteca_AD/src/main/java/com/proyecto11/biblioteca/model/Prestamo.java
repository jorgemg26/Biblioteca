package com.proyecto11.biblioteca.model;

import java.time.LocalDate;
public class Prestamo {
    private Long id;
    private Long usuarioId;
    private Integer libroId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDate fechaDevolucion;
    public Prestamo(Long usuarioId, Integer libroId, LocalDate fechaInicio, LocalDate
            fechaFin) {
        this.usuarioId = usuarioId;
        this.libroId = libroId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Long getId() {
        return id;
    }
    public Long getUsuarioId() {
        return usuarioId;
    }
    public Integer getLibroId() {
        return libroId;
    }
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }
    public void setId (Long id) {
        this.id = id;
    }
    public void setFechaDevolucion (LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}
