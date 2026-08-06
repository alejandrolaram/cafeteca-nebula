package com.cafeteca.nebula.controller;

import com.cafeteca.nebula.model.Libro;
import com.cafeteca.nebula.service.LibroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public String listarLibros(@RequestParam(required = false) Long editarId, Model model) {
        model.addAttribute("libros", libroService.obtenerTodos());

        if (editarId != null) {
            Libro libroEdit = libroService.obtenerPorId(editarId);
            model.addAttribute("libroEdit", libroEdit != null ? libroEdit : new Libro());
        } else {
            model.addAttribute("libroEdit", new Libro());
        }
        return "libros/lista";
    }

    @PostMapping("/guardar")
    public String guardarLibro(@ModelAttribute Libro libro, RedirectAttributes redirectAttributes) {
        try {
            boolean esNuevo = (libro.getId() == null);
            libroService.guardar(libro);
            redirectAttributes.addFlashAttribute("exito", esNuevo ? "Libro registrado exitosamente." : "Libro actualizado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al procesar el libro.");
        }
        return "redirect:/libros";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarLibro(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            libroService.eliminarPorId(id);
            redirectAttributes.addFlashAttribute("exito", "Libro y su historial eliminados correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el libro.");
        }
        return "redirect:/libros";
    }
}