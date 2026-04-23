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

@Controller
public class HomeController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private TipoEntradaService tipoEntradaService;

    @GetMapping("/")
    public String index(Model model) {

        // Obtener todos los eventos
        List<Evento> eventos = eventoService.listarEventos();

        // CALCULAR STOCK TOTAL PARA CADA EVENTO
        for (Evento e : eventos) {
            List<TipoEntrada> tipos = tipoEntradaService.listarPorEvento(e.getId_evento());

            int stockTotal = tipos.stream()
                    .mapToInt(TipoEntrada::getStock)
                    .sum();

            e.setStockTotal(stockTotal); // ← IMPORTANTE
        }

        // Filtrar solo eventos con imagen para el carrusel
        List<Evento> conImagen = eventos.stream()
                .filter(e -> e.getImagen() != null && !e.getImagen().isEmpty())
                .collect(Collectors.toList());

        // Mezclar aleatoriamente
        Collections.shuffle(conImagen);

        // Tomar 3 imágenes
        List<Evento> carrusel = conImagen.stream()
                .limit(3)
                .collect(Collectors.toList());

        model.addAttribute("carruselEventos", carrusel);

        // Eventos destacados (ya con stockTotal)
        model.addAttribute("eventosDestacados", eventos);

        return "index";
    }
}

