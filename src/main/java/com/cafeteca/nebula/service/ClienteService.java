package com.cafeteca.nebula.service;

import com.cafeteca.nebula.model.Cliente;
import com.cafeteca.nebula.model.Libro;
import com.cafeteca.nebula.model.Prestamo;
import com.cafeteca.nebula.repository.ClienteRepository;
import com.cafeteca.nebula.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final LibroRepository libroRepository;

    public ClienteService(ClienteRepository clienteRepository, LibroRepository libroRepository) {
        this.clienteRepository = clienteRepository;
        this.libroRepository = libroRepository;
    }

    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void eliminarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente != null) {
            // 1. Si el cliente tiene préstamos activos, liberamos los libros antes de borrar
            for (Prestamo prestamo : cliente.getPrestamos()) {
                if ("ACTIVO".equalsIgnoreCase(prestamo.getEstado())) {
                    Libro libro = prestamo.getLibro();
                    if (libro != null) {
                        libro.setDisponible(true);
                        libroRepository.save(libro);
                    }
                }
            }
            // 2. Eliminamos al cliente (sus préstamos se borran por la relación en Cliente.java)
            clienteRepository.delete(cliente);
        }
    }
}