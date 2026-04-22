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

@Controller
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @Autowired
    private TipoEntradaService tipoEntradaService;

    // FORMULARIO NUEVO
    @GetMapping("/nuevo/{idEvento}")
    public String nuevaCompra(@PathVariable int idEvento,
                              HttpSession session,
                              Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/login";

        Compra compra = new Compra();
        compra.setId_usuario(usuario.getId_usuario());
        compra.setDetalles(new ArrayList<>());
        compra.getDetalles().add(new DetalleCompra());

        model.addAttribute("compra", compra);
        model.addAttribute("idEvento", idEvento);
        model.addAttribute("tiposEntrada", tipoEntradaService.listarPorEvento(idEvento));

        return "compras/formulario";
    }

    // AÑADIR LÍNEA
    @PostMapping("/agregar-detalle")
    public String agregarDetalle(@ModelAttribute Compra compra,
                                 @RequestParam int idEvento,
                                 Model model) {

        compra.getDetalles().add(new DetalleCompra());

        model.addAttribute("compra", compra);
        model.addAttribute("idEvento", idEvento);
        model.addAttribute("tiposEntrada", tipoEntradaService.listarPorEvento(idEvento));

        return "compras/formulario";
    }

    // ELIMINAR LÍNEA
    @PostMapping("/eliminar-detalle")
    public String eliminarDetalle(@ModelAttribute Compra compra,
                                  @RequestParam int index,
                                  @RequestParam int idEvento,
                                  Model model) {

        if (index >= 0 && index < compra.getDetalles().size()) {
            compra.getDetalles().remove(index);
        }

        model.addAttribute("compra", compra);
        model.addAttribute("idEvento", idEvento);
        model.addAttribute("tiposEntrada", tipoEntradaService.listarPorEvento(idEvento));

        return "compras/formulario";
    }

    // GUARDAR COMPRA (CORREGIDO)
    @PostMapping("/guardar")
    public String guardarCompra(@ModelAttribute Compra compra,
                                @RequestParam int idEvento,
                                HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        compra.setId_usuario(usuario.getId_usuario());

        // ❌ ESTA PARTE SE ELIMINA PORQUE ROMPE LA ASIGNACIÓN DE ASIENTOS
        // for (DetalleCompra d : compra.getDetalles()) {
        //     d.setId_evento(idEvento);
        // }

        // ✔ PROCESAR COMPRA COMPLETA (asientos, stock, totales)
        compraService.procesarCompra(compra);

        session.setAttribute("ultimaCompra", compra);

        return "redirect:/compras/confirmacion";
    }

    // CONFIRMACIÓN
    @GetMapping("/confirmacion")
    public String mostrarConfirmacion(HttpSession session, Model model) {

        Compra compra = (Compra) session.getAttribute("ultimaCompra");

        if (compra == null) {
            return "redirect:/eventos";
        }

        model.addAttribute("compra", compra);

        return "compras/confirmacion";
    }

    @GetMapping("/mis-compras")
public String verMisCompras(HttpSession session, Model model) {

    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

    if (usuario == null) {
        return "redirect:/login";
    }

    // Obtener compras del usuario
    List<Compra> compras = compraService.listarComprasPorUsuario(usuario.getId_usuario());

    // Cargar detalles de cada compra
    for (Compra c : compras) {
        c.setDetalles(compraService.listarDetallesPorCompra(c.getId_compra()));
    }

    model.addAttribute("compras", compras);

    return "compras/mis-compras";
}

}

