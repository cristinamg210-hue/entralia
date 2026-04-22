package com.entralia.entralia.controller;

import com.entralia.entralia.model.Usuario;
import com.entralia.entralia.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email, HttpSession session) {

        // Buscar usuario por email
        Usuario usuario = usuarioService.buscarPorEmail(email);

        // Si no existe, lo creamos automáticamente
        if (usuario == null) {

            usuario = new Usuario();
            usuario.setNombre(email);
            usuario.setEmail(email);

            // IMPORTANTE: nuevo usuario = USER
            usuario.setRol("USER");

            usuarioService.guardarUsuario(usuario);

            // Recuperarlo desde la BD
            usuario = usuarioService.buscarPorEmail(email);
        }

        // Guardar usuario completo en sesión
        session.setAttribute("usuarioLogueado", usuario);

        // Guardar rol en sesión (para Thymeleaf y controladores)
        session.setAttribute("usuarioRol", usuario.getRol());

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}


