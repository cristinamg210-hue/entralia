package com.entralia.entralia.service;

import com.entralia.entralia.model.Asiento;
import com.entralia.entralia.model.Compra;
import com.entralia.entralia.model.DetalleCompra;
import com.entralia.entralia.model.Evento;
import com.entralia.entralia.repository.AsientoDAO;
import com.entralia.entralia.repository.CompraDAO;
import com.entralia.entralia.repository.DetalleCompraDAO;
import com.entralia.entralia.repository.EventoDAO;
import com.entralia.entralia.repository.TipoEntradaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraService {

    @Autowired
    @Qualifier("compraDAOJdbc")
    private CompraDAO compraDAO;

    @Autowired
    private DetalleCompraDAO detalleDAO;

    @Autowired
    private TipoEntradaDAO tipoEntradaDAO;

    @Autowired
    private EventoDAO eventoDAO;

    @Autowired
    @Qualifier("asientoDAOJdbc")
    private AsientoDAO asientoDAO;

    // PROCESA UNA COMPRA COMPLETA CON DETALLES
    public void procesarCompra(Compra compra) {

        // ⭐ LOG 1 — QUÉ DETALLES LLEGAN DEL FORMULARIO
        System.out.println("=== DETALLES RECIBIDOS ===");
        for (DetalleCompra d : compra.getDetalles()) {
            System.out.println("Detalle:");
            System.out.println(" - id_tipo_entrada: " + d.getId_tipo_entrada());
            System.out.println(" - id_evento (antes): " + d.getId_evento());
            System.out.println(" - cantidad: " + d.getCantidad());
        }
        System.out.println("===========================");

        // 1. Guardar compra y obtener ID generado
        int idCompra = compraDAO.guardar(compra);

        double totalCompra = 0.0;

        // 2. Procesar cada detalle
        for (DetalleCompra d : compra.getDetalles()) {

            // Obtener tipo de entrada completo
            var tipo = tipoEntradaDAO.obtenerPorId(d.getId_tipo_entrada());

            if (tipo == null) {
                throw new RuntimeException("Tipo de entrada no encontrado");
            }

            // Obtener el evento asociado
            Evento evento = eventoDAO.obtenerPorId(tipo.getId_evento());

            // ⭐ LOG 2 — ¿EL EVENTO USA ASIENTOS?
            System.out.println("Evento ID " + evento.getId_evento() +
                    " usa asientos: " + evento.isUsa_asientos());

            // Verificar stock
            if (d.getCantidad() > tipo.getStock()) {
                throw new RuntimeException("No hay stock suficiente para " + tipo.getNombre());
            }

            // Asignar precio unitario
            d.setPrecio_unitario(tipo.getPrecio());

            // Calcular total del detalle
            double totalDetalle = d.getCantidad() * tipo.getPrecio();
            totalCompra += totalDetalle;

            // Asignar compra y tipoEntrada
            d.setId_compra(idCompra);
            d.setTipoEntrada(tipo);

            // asignar id_evento al detalle
            d.setId_evento(tipo.getId_evento());

            // ASIGNACIÓN DE ASIENTO (solo si el evento usa asientos)
            if (evento.isUsa_asientos()) {

                // Solo permitimos 1 entrada por compra en eventos con asiento
                if (d.getCantidad() > 1) {
                    throw new RuntimeException("Los eventos con asiento solo permiten 1 entrada por compra.");
                }

                // Buscar asiento libre
                Asiento asientoLibre = asientoDAO.obtenerAsientoLibre(evento.getId_evento());

                // ⭐ LOG 3 — ¿QUÉ ASIENTO SE HA ENCONTRADO?
                System.out.println("Asiento libre encontrado: " +
                        (asientoLibre != null ? asientoLibre.getNumero() : "NINGUNO"));

                if (asientoLibre == null) {
                    throw new RuntimeException("No quedan asientos disponibles para este evento.");
                }

                // Marcar asiento como ocupado
                asientoDAO.marcarOcupado(asientoLibre.getId_asiento());

                // Guardar asiento en el detalle
                d.setAsiento(asientoLibre.getNumero());

                // ⭐ LOG 4 — ASIENTO ASIGNADO
                System.out.println("Asiento asignado: " + d.getAsiento());
            }

            // ⭐ LOG 5 — JUSTO ANTES DE GUARDAR EL DETALLE
            System.out.println("Guardando detalle con asiento: " + d.getAsiento());

            // Guardar detalle
            detalleDAO.guardar(d);
        }

        // 3. Guardar total de la compra
        compra.setTotal(totalCompra);

        // 4. Actualizar compra
        compra.setId_compra(idCompra);
        compraDAO.actualizar(compra);
    }

    public int guardarCompra(Compra compra) {
        return compraDAO.guardar(compra);
    }

    public void actualizarCompra(Compra compra) {
        compraDAO.actualizar(compra);
    }

    public void eliminarCompra(int id) {
        compraDAO.eliminar(id);
    }

    public Compra obtenerCompraPorId(int id) {
        return compraDAO.obtenerPorId(id);
    }

    public List<Compra> listarCompras() {
        return compraDAO.listarTodos();
    }

    public List<Compra> listarComprasPorUsuario(int idUsuario) {
        return compraDAO.listarComprasPorUsuario(idUsuario);
    }

    public List<DetalleCompra> listarDetallesPorCompra(int idCompra) {
        return detalleDAO.listarPorCompra(idCompra);
    }

}
