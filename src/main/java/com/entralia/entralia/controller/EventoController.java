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

@Controller // Controlador que gestiona todo lo relacionado con eventos
@RequestMapping("/eventos") // Todas las rutas empiezan por /eventos
public class EventoController {

    @Autowired
    private EventoService eventoService; // Servicio con la lógica de eventos

    @Autowired
    private TipoEntradaService tipoEntradaService; // Servicio para tipos de entrada

    private final String RUTA_IMAGENES = "C:/entralia/img/eventos/"; // Carpeta donde guardo imágenes

    // LISTAR EVENTOS (Explorar eventos)
    @GetMapping
    public String listarEventos(Model model) {

        List<Evento> eventos = eventoService.listarEventos(); // Obtengo todos los eventos

        // Calculo el stock total sumando el stock de cada tipo de entrada

        for (Evento e : eventos) {
            List<TipoEntrada> tipos = tipoEntradaService.listarPorEvento(e.getId_evento());

            int stockTotal = tipos.stream()
                    .mapToInt(TipoEntrada::getStock)
                    .sum();

            e.setStockTotal(stockTotal); // Guardo el stock total en el evento
        }

        model.addAttribute("eventos", eventos);
        return "eventos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model, HttpSession session) {

        // Solo los administradores pueden crear eventos

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
        
        // Solo ADMIN puede guardar

        if (!"ADMIN".equals(session.getAttribute("usuarioRol"))) {
            return "redirect:/eventos";
        }

        // Si el usuario sube una imagen, la guardamos en la carpeta

        if (!imagenFile.isEmpty()) {

            Path directorio = Paths.get(RUTA_IMAGENES);
            if (!Files.exists(directorio)) {
                Files.createDirectories(directorio); // Crea la carpeta si no existe
            }

            String nombreArchivo = imagenFile.getOriginalFilename();
            Path ruta = Paths.get(RUTA_IMAGENES + nombreArchivo);
            Files.write(ruta, imagenFile.getBytes()); // Guarda la imagen

            evento.setImagen(nombreArchivo); // Guardamos el nombre en la BD
        }

        eventoService.guardarEvento(evento); // Guardamos el evento
        return "redirect:/eventos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable int id, Model model, HttpSession session) {

        if (!"ADMIN".equals(session.getAttribute("usuarioRol"))) {
            return "redirect:/eventos";
        }

        // Si se sube una nueva imagen, la guardamos igual que antes

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

        eventoService.eliminarEvento(id); // Elimina el evento
        return "redirect:/eventos";
    }

    // DETALLE DEL EVENTO
    @GetMapping("/{id}")
    public String verDetalleEvento(@PathVariable("id") Integer id, Model model) {

        Evento evento = eventoService.obtenerEventoPorId(id); // Busco el evento
        model.addAttribute("evento", evento);

        List<TipoEntrada> tipos = tipoEntradaService.listarPorEvento(id); // Tipos de entrada del evento
        model.addAttribute("tiposEntrada", tipos);

        // Calculo el stock total del evento

        int stockTotal = tipos.stream()
                .mapToInt(TipoEntrada::getStock)
                .sum();

        model.addAttribute("stockTotal", stockTotal);

        return "eventos/detalle"; // Vista detalle.html
    }

}
