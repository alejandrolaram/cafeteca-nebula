package com.cafeteca.nebula.controller;

import com.cafeteca.nebula.model.Prestamo;
import com.cafeteca.nebula.model.Cliente;
import com.cafeteca.nebula.model.Libro;
import com.cafeteca.nebula.repository.PrestamoRepository;
import com.cafeteca.nebula.repository.ClienteRepository;
import com.cafeteca.nebula.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private LibroRepository libroRepository;

    @GetMapping
    public String listarPrestamos(Model model) {
        model.addAttribute("prestamos", prestamoRepository.findAll());
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("libros", libroRepository.findByDisponibleTrue());
        return "prestamos/lista";
    }

    @PostMapping("/guardar")
    public String registrarPrestamo(@RequestParam Long clienteId,
                                    @RequestParam Long libroId,
                                    RedirectAttributes redirectAttributes) {

        // Regla 1: Validar que el cliente tenga como máximo 1 préstamo activo
        if (prestamoRepository.existsByClienteIdAndEstado(clienteId, "ACTIVO")) {
            redirectAttributes.addFlashAttribute("error", "El cliente ya tiene un préstamo activo. Límite: 1 libro por cliente.");
            return "redirect:/prestamos";
        }

        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        Libro libro = libroRepository.findById(libroId).orElse(null);

        if (cliente != null && libro != null) {
            Prestamo prestamo = new Prestamo();
            prestamo.setCliente(cliente);
            prestamo.setLibro(libro);
            prestamo.setFechaPrestamo(LocalDate.now());

            // Regla 2: Establecer fecha límite de devolución (7 días)
            prestamo.setFechaLimite(LocalDate.now().plusDays(7));
            prestamo.setEstado("ACTIVO");

            // Actualizar disponibilidad del libro
            libro.setDisponible(false);
            libroRepository.save(libro);

            prestamoRepository.save(prestamo);
            redirectAttributes.addFlashAttribute("exito", "Préstamo registrado exitosamente.");
        }

        return "redirect:/prestamos";
    }

    @PostMapping("/devolver/{id}")
    public String devolverLibro(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Prestamo prestamo = prestamoRepository.findById(id).orElse(null);

        if (prestamo != null && "ACTIVO".equalsIgnoreCase(prestamo.getEstado())) {
            prestamo.setEstado("DEVUELTO");
            prestamo.setFechaDevolucion(LocalDate.now());

            Libro libro = prestamo.getLibro();
            libro.setDisponible(true);
            libroRepository.save(libro);

            prestamoRepository.save(prestamo);
            redirectAttributes.addFlashAttribute("exito", "Libro devuelto con éxito.");
        }

        return "redirect:/prestamos";
    }
}