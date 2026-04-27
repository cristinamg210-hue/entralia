/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entralia.entralia.controller;

import com.entralia.entralia.model.Usuario;
import com.entralia.entralia.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // Controlador que gestiona los usuarios
@RequestMapping("/usuarios") // Todas las rutas empiezan por /usuarios
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService; // Servicio con la lógica de usuarios

    // LISTAR
    @GetMapping // Muestra la lista de usuarios

    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        return "usuarios/lista";
    }

    // FORMULARIO NUEVO
    @GetMapping("/nuevo") // Muestra el formulario para crear un usuario

    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/formulario";
    }

    // GUARDAR
    @PostMapping("/guardar") // Guarda un usuario nuevo

    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.guardarUsuario(usuario);
        return "redirect:/usuarios";
    }

    // FORMULARIO EDITAR
    @GetMapping("/editar/{id}") // Muestra el formulario para editar un usuario

    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
        model.addAttribute("usuario", usuarioService.obtenerUsuarioPorId(id));
        return "usuarios/formulario";
    }

    // ACTUALIZAR
    @PostMapping("/actualizar") // Actualiza un usuario existente

    public String actualizarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.actualizarUsuario(usuario);
        return "redirect:/usuarios";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}") // Elimina un usuario por su ID
    
    public String eliminarUsuario(@PathVariable int id) {
        usuarioService.eliminarUsuario(id);
        return "redirect:/usuarios";
    }

    // PERFIL DEL USUARIO LOGUEADO
    @GetMapping("/perfil") // Muestra el perfil del usuario que ha iniciado sesión
    public String perfilUsuario(HttpSession session, Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        // Si no hay usuario en sesión, lo mando al login

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario); // Envío los datos del usuario a la vista

        return "usuarios/perfil"; // Vista perfil.html
    }
}

