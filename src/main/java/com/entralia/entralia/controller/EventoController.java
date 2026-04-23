/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entralia.entralia.controller;

import com.entralia.entralia.model.Evento;
import com.entralia.entralia.model.TipoEntrada;
import com.entralia.entralia.service.EventoService;
import com.entralia.entralia.service.TipoEntradaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private TipoEntradaService tipoEntradaService;

    private final String RUTA_IMAGENES = "C:/entralia/img/eventos/";

    // LISTAR EVENTOS (Explorar eventos)
    @GetMapping
    public String listarEventos(Model model) {

        List<Evento> eventos = eventoService.listarEventos();

        // Calcular stockTotal para cada evento
        for (Evento e : eventos) {
            List<TipoEntrada> tipos = tipoEntradaService.listarPorEvento(e.getId_evento());

            int stockTotal = tipos.stream()
                    .mapToInt(TipoEntrada::getStock)
                    .sum();

            e.setStockTotal(stockTotal);
        }

        model.addAttribute("eventos", eventos);
        return "eventos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model, HttpSession session) {

        if (!"ADMIN".equals(session.getAttribute("usuarioRol"))) {
            return "redirect:/eventos";
        }

        model.addAttribute("evento", new Evento());
        return "eventos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarEvento(@ModelAttribute Evento evento,
                                @RequestParam("imagenFile") MultipartFile imagenFile,
                                HttpSession session) throws IOException {

        if (!"ADMIN".equals(session.getAttribute("usuarioRol"))) {
            return "redirect:/eventos";
        }

        if (!imagenFile.isEmpty()) {

            Path directorio = Paths.get(RUTA_IMAGENES);
            if (!Files.exists(directorio)) {
                Files.createDirectories(directorio);
            }

            String nombreArchivo = imagenFile.getOriginalFilename();
            Path ruta = Paths.get(RUTA_IMAGENES + nombreArchivo);
            Files.write(ruta, imagenFile.getBytes());

            evento.setImagen(nombreArchivo);
        }

        eventoService.guardarEvento(evento);
        return "redirect:/eventos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable int id, Model model, HttpSession session) {

        if (!"ADMIN".equals(session.getAttribute("usuarioRol"))) {
            return "redirect:/eventos";
        }

        model.addAttribute("evento", eventoService.obtenerEventoPorId(id));
        return "eventos/formulario";
    }

    @PostMapping("/actualizar")
    public String actualizarEvento(@ModelAttribute Evento evento,
                                   @RequestParam("imagenFile") MultipartFile imagenFile,
                                   HttpSession session) throws IOException {

        if (!"ADMIN".equals(session.getAttribute("usuarioRol"))) {
            return "redirect:/eventos";
        }

        if (!imagenFile.isEmpty()) {

            Path directorio = Paths.get(RUTA_IMAGENES);
            if (!Files.exists(directorio)) {
                Files.createDirectories(directorio);
            }

            String nombreArchivo = imagenFile.getOriginalFilename();
            Path ruta = Paths.get(RUTA_IMAGENES + nombreArchivo);
            Files.write(ruta, imagenFile.getBytes());

            evento.setImagen(nombreArchivo);
        }

        eventoService.actualizarEvento(evento);
        return "redirect:/eventos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEvento(@PathVariable int id, HttpSession session) {

        if (!"ADMIN".equals(session.getAttribute("usuarioRol"))) {
            return "redirect:/eventos";
        }

        eventoService.eliminarEvento(id);
        return "redirect:/eventos";
    }

    // DETALLE DEL EVENTO
    @GetMapping("/{id}")
    public String verDetalleEvento(@PathVariable("id") Integer id, Model model) {

        Evento evento = eventoService.obtenerEventoPorId(id);
        model.addAttribute("evento", evento);

        List<TipoEntrada> tipos = tipoEntradaService.listarPorEvento(id);
        model.addAttribute("tiposEntrada", tipos);

        int stockTotal = tipos.stream()
                .mapToInt(TipoEntrada::getStock)
                .sum();

        model.addAttribute("stockTotal", stockTotal);

        return "eventos/detalle";
    }

}
