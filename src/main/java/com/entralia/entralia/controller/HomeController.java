package com.entralia.entralia.controller;

import com.entralia.entralia.model.Evento;
import com.entralia.entralia.model.TipoEntrada;
import com.entralia.entralia.service.EventoService;
import com.entralia.entralia.service.TipoEntradaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller // Controlador para la página principal
public class HomeController {

    @Autowired
    private EventoService eventoService; // Servicio para obtener eventos

    @Autowired
    private TipoEntradaService tipoEntradaService; // Servicio para tipos de entrada

    @GetMapping("/") // Ruta principal del proyecto

    public String index(Model model) {

        // Obtengo todos los eventos de la base de datos
        List<Evento> eventos = eventoService.listarEventos();

        // CALCULAR STOCK TOTAL PARA CADA EVENTO
        for (Evento e : eventos) {
            List<TipoEntrada> tipos = tipoEntradaService.listarPorEvento(e.getId_evento());

            int stockTotal = tipos.stream()
                    .mapToInt(TipoEntrada::getStock)
                    .sum();

            e.setStockTotal(stockTotal); // Guardo el stock total en el evento
        }

        // Filtrar solo eventos con imagen para el carrusel
        List<Evento> conImagen = eventos.stream()
                .filter(e -> e.getImagen() != null && !e.getImagen().isEmpty())
                .collect(Collectors.toList());

        // Mezclo aleatoriamente para que el carrusel cambie cada vez

        Collections.shuffle(conImagen);

        // Selecciono solo 3 imágenes para el carrusel

        List<Evento> carrusel = conImagen.stream()
                .limit(3)
                .collect(Collectors.toList());

        model.addAttribute("carruselEventos", carrusel); // Envío el carrusel a la vista

        // Envío todos los eventos como "destacados"
        model.addAttribute("eventosDestacados", eventos);

        return "index"; // Carga la vista index.html
    }
}

