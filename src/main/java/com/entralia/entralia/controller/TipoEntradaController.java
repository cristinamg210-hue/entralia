/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entralia.entralia.controller;

import com.entralia.entralia.model.TipoEntrada;
import com.entralia.entralia.service.TipoEntradaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // Controlador que gestiona los tipos de entrada
@RequestMapping("/tipos-entrada") // Todas las rutas empiezan por /tipos-entrada
public class TipoEntradaController {

    @Autowired
    private TipoEntradaService tipoEntradaService; // Servicio con la lógica de negocio

    // LISTAR
    @GetMapping // Muestra la lista de tipos de entrada

    public String listarTiposEntrada(Model model) {
        model.addAttribute("tiposEntrada", tipoEntradaService.listarTipoEntradas());
        return "tipos-entrada/lista"; // Vista lista.html
    }

    // FORMULARIO NUEVO
    @GetMapping("/nuevo") // Muestra el formulario para crear un tipo de entrada

    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("tipoEntrada", new TipoEntrada());
        return "tipos-entrada/formulario";
    }

    // GUARDAR
    @PostMapping("/guardar") // Guarda un nuevo tipo de entrada
    public String guardarTipoEntrada(@ModelAttribute TipoEntrada tipoEntrada) {
        tipoEntradaService.guardarTipoEntrada(tipoEntrada);
        return "redirect:/tipos-entrada"; // Redirige a la lista
    }

    // FORMULARIO EDITAR
    @GetMapping("/editar/{id}") // Muestra el formulario para editar un tipo existente

    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("tipoEntrada", tipoEntradaService.obtenerTipoEntradaPorId(id));
        return "tipos-entrada/formulario";
    }

    // ACTUALIZAR
    @PostMapping("/actualizar") // Actualiza un tipo de entrada ya existente

    public String actualizarTipoEntrada(@ModelAttribute TipoEntrada tipoEntrada) {
        tipoEntradaService.actualizarTipoEntrada(tipoEntrada);
        return "redirect:/tipos-entrada";
    }

    // ELIMINAR 
    @GetMapping("/eliminar/{id}") // Elimina un tipo de entrada por ID
    public String eliminarTipoEntrada(@PathVariable int id) {
        tipoEntradaService.eliminarTipoEntrada(id);
        return "redirect:/tipos-entrada";
    }
}
