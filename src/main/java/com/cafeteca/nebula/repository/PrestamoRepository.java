package com.cafeteca.nebula.repository;

import com.cafeteca.nebula.model.Cliente;
import com.cafeteca.nebula.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByEstado(String estado);
    Optional<Prestamo> findByClienteAndEstadoIn(Cliente cliente, List<String> estados);
}