/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entralia.entralia.controller;

import com.entralia.entralia.model.DetalleCompra;
import com.entralia.entralia.service.DetalleCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author cristinamg210
 */
@Controller // Gestiona todo lo relacionado con DetalleCompra
@RequestMapping("/detalles-compra") // Todas las rutas empiezan por /detalles-compra
public class DetalleCompraController {

    @Autowired
    private DetalleCompraService detalleCompraService; // Servicio con la lógica de negocio
    
    @GetMapping // Lista todos los detalles de compra

    public String listarDetalleCompras(Model model) {
        model.addAttribute("detallesCompra", detalleCompraService.listarDetalleCompras());

        return "detalles-compra/lista"; // Carga la vista lista.html
    }

    @GetMapping("/nuevo") // Muestra el formulario para crear un detalle nuevo
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("detalleCompra", new DetalleCompra());
        return "detalles-compra/formulario";
    }

    @PostMapping("/guardar") // Guarda un nuevo detalle en la BD

    public String guardarDetalleCompra(@ModelAttribute DetalleCompra detalle) {
        detalleCompraService.guardarDetalleCompra(detalle);

        return "redirect:/detalles-compra"; // Redirige a la lista después de guardar
    }

    @GetMapping("/editar/{id}") // Muestra el formulario para editar un detalle existente

    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("detalleCompra", detalleCompraService.obtenerDetalleCompraPorId(id));
        return "detalles-compra/formulario";
    }

    @PostMapping("/actualizar") // Actualiza un detalle ya existente

    public String actualizarDetalleCompra(@ModelAttribute DetalleCompra detalle) {
        detalleCompraService.actualizarDetalleCompra(detalle);
        return "redirect:/detalles-compra";
    }

    @GetMapping("/eliminar/{id}") // Elimina un detalle por su ID
    
    public String eliminarDetalleCompra(@PathVariable int id) {
        detalleCompraService.eliminarDetalleCompra(id);
        return "redirect:/detalles-compra";
    }

    
    
}
