package com.cafeteca.nebula.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private LocalDate fechaLimite;

    private String estado; // "ACTIVO", "DEVUELTO"

    // 1. Constructor vacío (requerido por JPA/Hibernate)
    public Prestamo() {}

    // 2. Constructor con los 4 parámetros que exige PrestamoService.java
    public Prestamo(Cliente cliente, Libro libro, LocalDate fechaPrestamo, String estado) {
        this.cliente = cliente;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaPrestamo != null ? fechaPrestamo.plusDays(7) : null;
        this.estado = estado;
    }

    // Método para saber si está retrasado actualmente
    public boolean isRetrasado() {
        return "ACTIVO".equalsIgnoreCase(this.estado)
                && this.fechaLimite != null
                && LocalDate.now().isAfter(this.fechaLimite);
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Libro getLibro() { return libro; }
    public void setLibro(Libro libro) { this.libro = libro; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public LocalDate getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(LocalDate fechaLimite) { this.fechaLimite = fechaLimite; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}