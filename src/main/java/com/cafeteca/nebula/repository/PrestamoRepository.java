package com.cafeteca.nebula.repository;

import com.cafeteca.nebula.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    // Método para validar que un cliente no sobrepase el límite de 1 préstamo activo
    boolean existsByClienteIdAndEstado(Long clienteId, String estado);

    List<Prestamo> findByEstado(String estado);
}