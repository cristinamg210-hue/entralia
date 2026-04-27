package com.entralia.entralia.controller;

import com.entralia.entralia.model.Usuario;
import com.entralia.entralia.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // Controlador encargado del login y logout
public class LoginController {

    @Autowired
    private UsuarioService usuarioService; // Servicio para buscar y guardar usuarios

    @GetMapping("/login") // Muestra la página de login
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login") // Procesa el formulario de login
    
    public String procesarLogin(@RequestParam String email, HttpSession session) {

        // Buscar usuario por email
        Usuario usuario = usuarioService.buscarPorEmail(email);

        // Si no existe, lo creamos automáticamente
        if (usuario == null) {

            usuario = new Usuario();
            usuario.setNombre(email);
            usuario.setEmail(email);

            
            usuario.setRol("USER"); // Los nuevos usuarios siempre son USER

            usuarioService.guardarUsuario(usuario); // Guardamos en la BD

            // Lo recuperamos ya con su ID generado

            usuario = usuarioService.buscarPorEmail(email);
        }

        // Guardamos el usuario en sesión
        session.setAttribute("usuarioLogueado", usuario);

        // Guardamos también el rol (para controlar permisos)
        session.setAttribute("usuarioRol", usuario.getRol());

        return "redirect:/"; // Redirige a la página principal
    }

    @GetMapping("/logout") // Cierra sesión
    public String logout(HttpSession session) {
        session.invalidate(); // Borra todos los datos de sesión
        return "redirect:/login"; // Vuelve al login
    }
}


