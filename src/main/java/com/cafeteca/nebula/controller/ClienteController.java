package com.cafeteca.nebula.controller;

import com.cafeteca.nebula.model.Cliente;
import com.cafeteca.nebula.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listarClientes(@RequestParam(required = false) Long editarId, Model model) {
        model.addAttribute("clientes", clienteService.obtenerTodos());

        if (editarId != null) {
            Cliente clienteEdit = clienteService.obtenerPorId(editarId);
            model.addAttribute("clienteEdit", clienteEdit != null ? clienteEdit : new Cliente());
        } else {
            model.addAttribute("clienteEdit", new Cliente());
        }
        return "clientes/lista";
    }

    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        try {
            boolean esNuevo = (cliente.getId() == null);
            clienteService.guardar(cliente);
            redirectAttributes.addFlashAttribute("exito", esNuevo ? "Cliente registrado exitosamente." : "Cliente actualizado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el cliente.");
        }
        return "redirect:/clientes";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            clienteService.eliminarPorId(id);
            redirectAttributes.addFlashAttribute("exito", "Cliente y su historial eliminados correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el cliente.");
        }
        return "redirect:/clientes";
    }
}