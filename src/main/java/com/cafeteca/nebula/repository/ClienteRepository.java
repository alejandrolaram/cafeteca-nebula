package com.cafeteca.nebula.repository;

import com.cafeteca.nebula.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}