package com.cafeteca.nebula.service;

import com.cafeteca.nebula.model.Cliente;
import com.cafeteca.nebula.model.Libro;
import com.cafeteca.nebula.model.Prestamo;
import com.cafeteca.nebula.repository.ClienteRepository;
import com.cafeteca.nebula.repository.LibroRepository;
import com.cafeteca.nebula.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Prestamo> obtenerTodos() {
        return prestamoRepository.findAll();
    }

    @Transactional
    public Prestamo registrarPrestamo(Long clienteId, Long libroId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));

        if (!libro.isDisponible()) {
            throw new IllegalStateException("El libro seleccionado no está disponible para préstamo");
        }

        // Marcar el libro como no disponible
        libro.setDisponible(false);
        libroRepository.save(libro);

        // Crear el nuevo registro de préstamo
        Prestamo prestamo = new Prestamo(cliente, libro, LocalDate.now(), "ACTIVO");
        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public void registrarDevolucion(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

        if ("DEVUELTO".equals(prestamo.getEstado())) {
            throw new IllegalStateException("Este préstamo ya fue devuelto previamente");
        }

        // Actualizar el estado del préstamo
        prestamo.setFechaDevolucion(LocalDate.now());
        prestamo.setEstado("DEVUELTO");

        // Reactivar la disponibilidad del libro
        Libro libro = prestamo.getLibro();
        libro.setDisponible(true);

        libroRepository.save(libro);
        prestamoRepository.save(prestamo);
    }
}