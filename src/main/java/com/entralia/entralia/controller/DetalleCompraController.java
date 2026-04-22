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
@Controller
@RequestMapping("/detalles-compra")
public class DetalleCompraController {

    @Autowired
    private DetalleCompraService detalleCompraService;
    @GetMapping
    public String listarDetalleCompras(Model model) {
        model.addAttribute("detallesCompra", detalleCompraService.listarDetalleCompras());
        return "detalles-compra/lista"; // vista Thymeleaf
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("detalleCompra", new DetalleCompra());
        return "detalles-compra/formulario";
    }

    @PostMapping("/guardar")
    public String guardarDetalleCompra(@ModelAttribute DetalleCompra detalle) {
        detalleCompraService.guardarDetalleCompra(detalle);
        return "redirect:/detalles-compra";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("detalleCompra", detalleCompraService.obtenerDetalleCompraPorId(id));
        return "detalles-compra/formulario";
    }

    @PostMapping("/actualizar")
    public String actualizarDetalleCompra(@ModelAttribute DetalleCompra detalle) {
        detalleCompraService.actualizarDetalleCompra(detalle);
        return "redirect:/detalles-compra";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarDetalleCompra(@PathVariable int id) {
        detalleCompraService.eliminarDetalleCompra(id);
        return "redirect:/detalles-compra";
    }

    
    
}
