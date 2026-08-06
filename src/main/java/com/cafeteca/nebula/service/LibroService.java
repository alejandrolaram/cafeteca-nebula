package com.cafeteca.nebula.service;

import com.cafeteca.nebula.model.Libro;
import com.cafeteca.nebula.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }

    public Libro obtenerPorId(Long id) {
        return libroRepository.findById(id).orElse(null);
    }

    public Libro guardar(Libro libro) {
        if (libro.getId() == null) {
            libro.setDisponible(true);
        } else {
            // Si se está editando, preservamos el estado de disponibilidad existente
            Libro existente = obtenerPorId(libro.getId());
            if (existente != null && libro.getDisponible() == null) {
                libro.setDisponible(existente.getDisponible());
            }
        }
        return libroRepository.save(libro);
    }

    public void eliminarPorId(Long id) {
        libroRepository.deleteById(id);
    }
}