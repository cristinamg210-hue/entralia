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

@Controller
@RequestMapping("/tipos-entrada")
public class TipoEntradaController {

    @Autowired
    private TipoEntradaService tipoEntradaService;

    // LISTAR
    @GetMapping
    public String listarTiposEntrada(Model model) {
        model.addAttribute("tiposEntrada", tipoEntradaService.listarTipoEntradas());
        return "tipos-entrada/lista";
    }

    // FORMULARIO NUEVO
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("tipoEntrada", new TipoEntrada());
        return "tipos-entrada/formulario";
    }

    // GUARDAR
    @PostMapping("/guardar")
    public String guardarTipoEntrada(@ModelAttribute TipoEntrada tipoEntrada) {
        tipoEntradaService.guardarTipoEntrada(tipoEntrada);
        return "redirect:/tipos-entrada";
    }

    // FORMULARIO EDITAR
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("tipoEntrada", tipoEntradaService.obtenerTipoEntradaPorId(id));
        return "tipos-entrada/formulario";
    }

    // ACTUALIZAR
    @PostMapping("/actualizar")
    public String actualizarTipoEntrada(@ModelAttribute TipoEntrada tipoEntrada) {
        tipoEntradaService.actualizarTipoEntrada(tipoEntrada);
        return "redirect:/tipos-entrada";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarTipoEntrada(@PathVariable int id) {
        tipoEntradaService.eliminarTipoEntrada(id);
        return "redirect:/tipos-entrada";
    }
}
