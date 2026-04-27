package com.entralia.entralia.controller;

import com.entralia.entralia.model.Compra;
import com.entralia.entralia.model.DetalleCompra;
import com.entralia.entralia.model.Usuario;
import com.entralia.entralia.service.CompraService;
import com.entralia.entralia.service.TipoEntradaService;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // Indica que esta clase es un controlador de Spring MVC
@RequestMapping("/compras") // Todas las rutas empiezan por /compras
public class CompraController {

    @Autowired
    private CompraService compraService; // Servicio que gestiona la lógica de compras

    @Autowired
    private TipoEntradaService tipoEntradaService; // Servicio para obtener tipos de entrada

    @GetMapping("/nuevo/{idEvento}") // Muestra el formulario de compra para un evento específico
    public String nuevaCompra(@PathVariable int idEvento,
                              HttpSession session,
                              Model model) {
        
        // Comprobamos si el usuario está logueado

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";
        
        // Creamos una compra nueva con un detalle vacío
        Compra compra = new Compra();
        compra.setId_usuario(usuario.getId_usuario());
        compra.setDetalles(new ArrayList<>());
        compra.getDetalles().add(new DetalleCompra());

        // Enviamos datos a la vista

        model.addAttribute("compra", compra);
        model.addAttribute("idEvento", idEvento);
        model.addAttribute("tiposEntrada", tipoEntradaService.listarPorEvento(idEvento));

        return "compras/formulario"; // Carga la vista del formulario
    }

    @PostMapping("/agregar-detalle") // Añade una línea más al formulario
    public String agregarDetalle(@ModelAttribute Compra compra,
                                 @RequestParam int idEvento,
                                 Model model) {

        compra.getDetalles().add(new DetalleCompra()); // Añadimos un nuevo detalle

        model.addAttribute("compra", compra);
        model.addAttribute("idEvento", idEvento);
        model.addAttribute("tiposEntrada", tipoEntradaService.listarPorEvento(idEvento));

        return "compras/formulario";
    }

    @PostMapping("/eliminar-detalle") // Elimina una línea del formulario
    public String eliminarDetalle(@ModelAttribute Compra compra,
                                  @RequestParam int index,
                                  @RequestParam int idEvento,
                                  Model model) {

        // Eliminamos el detalle si el índice es válido

        if (index >= 0 && index < compra.getDetalles().size()) {
            compra.getDetalles().remove(index);
        }

        model.addAttribute("compra", compra);
        model.addAttribute("idEvento", idEvento);
        model.addAttribute("tiposEntrada", tipoEntradaService.listarPorEvento(idEvento));

        return "compras/formulario";
    }

    @PostMapping("/guardar") // Guarda la compra en la base de datos
    public String guardarCompra(@ModelAttribute Compra compra,
                                @RequestParam int idEvento,
                                HttpSession session) {

        // Comprobamos si el usuario está logueado

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";

        compra.setId_usuario(usuario.getId_usuario()); // Asignamos el usuario

        compraService.procesarCompra(compra); // Lógica completa de guardado

        session.setAttribute("ultimaCompra", compra); // Guardamos para la confirmación

        return "redirect:/compras/confirmacion";
    }

    @GetMapping("/confirmacion") // Muestra la página de confirmación
    public String mostrarConfirmacion(HttpSession session, Model model) {

        Compra compra = (Compra) session.getAttribute("ultimaCompra");

        if (compra == null) return "redirect:/eventos";

        model.addAttribute("compra", compra);

        return "compras/confirmacion";
    }

    @GetMapping("/mis-compras") // Lista todas las compras del usuario
    public String verMisCompras(HttpSession session, Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";

        // Obtenemos las compras del usuario

        List<Compra> compras = compraService.listarComprasPorUsuario(usuario.getId_usuario());

        // Cargamos los detalles de cada compra

        for (Compra c : compras) {
            c.setDetalles(compraService.listarDetallesPorCompra(c.getId_compra()));
        }

        model.addAttribute("compras", compras);

        return "compras/mis-compras";
    }
}


