package com.entralia.entralia.controller;

import com.entralia.entralia.service.EventoService;
import com.entralia.entralia.model.Evento;
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

    @GetMapping("/")
public String index(Model model) {

    List<Evento> eventos = eventoService.listarEventos();

    // Filtrar solo eventos con imagen
    List<Evento> conImagen = eventos.stream()
            .filter(e -> e.getImagen() != null && !e.getImagen().isEmpty())
            .collect(Collectors.toList()); // ← IMPORTANTE

    // Mezclar aleatoriamente
    Collections.shuffle(conImagen);

    // Tomar 3 imágenes
    List<Evento> carrusel = conImagen.stream()
            .limit(3)
            .collect(Collectors.toList()); // ← IMPORTANTE

    model.addAttribute("carruselEventos", carrusel);

    // Tus destacados
    model.addAttribute("eventosDestacados", eventos);

    return "index";
}


}
