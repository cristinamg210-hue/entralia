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

import java.util.ArrayList;
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

    // PROCESAR COMPRA COMPLETA
    public void procesarCompra(Compra compra) {

        // 1. Guardar compra y obtener ID generado
        int idCompra = compraDAO.guardar(compra);

        double totalCompra = 0.0;

        // Lista final de detalles (ya divididos si hay asientos)
        List<DetalleCompra> detallesFinales = new ArrayList<>();

        // 2. Procesar cada detalle recibido del formulario
        for (DetalleCompra d : compra.getDetalles()) {

            // Cargar tipo de entrada

            var tipo = tipoEntradaDAO.obtenerPorId(d.getId_tipo_entrada());
            if (tipo == null) {
                throw new RuntimeException("Tipo de entrada no encontrado");
            }

            // Cargar evento asociado

            Evento evento = eventoDAO.obtenerPorId(tipo.getId_evento());

            // Verificar stock
            if (d.getCantidad() > tipo.getStock()) {
                throw new RuntimeException("No hay stock suficiente para " + tipo.getNombre());
            }

            // EVENTO SIN ASIENTOS → procesar normal
            if (!evento.isUsa_asientos()) {

                d.setId_compra(idCompra);
                d.setId_evento(evento.getId_evento());
                d.setPrecio_unitario(tipo.getPrecio());
                d.setTipoEntrada(tipo);

                totalCompra += d.getPrecioTotal();
                detallesFinales.add(d);
                continue;
            }

            // EVENTO CON ASIENTOS → dividir en entradas individuales
            for (int i = 0; i < d.getCantidad(); i++) {

                // Buscar asiento libre

                Asiento asientoLibre = asientoDAO.obtenerAsientoLibre(evento.getId_evento());
                if (asientoLibre == null) {
                    throw new RuntimeException("No quedan asientos disponibles.");
                }

                // Crear un detalle por cada asiento

                DetalleCompra nuevo = new DetalleCompra();
                nuevo.setId_compra(idCompra);
                nuevo.setId_evento(evento.getId_evento());
                nuevo.setId_tipo_entrada(tipo.getId_tipo_entrada());
                nuevo.setCantidad(1);
                nuevo.setPrecio_unitario(tipo.getPrecio());
                nuevo.setTipoEntrada(tipo);
                nuevo.setAsiento(asientoLibre.getNumero());

                // Marcar asiento como ocupado
                
                asientoDAO.marcarOcupado(asientoLibre.getId_asiento());

                totalCompra += nuevo.getPrecioTotal();
                detallesFinales.add(nuevo);
            }
        }

        // 3. Guardar todos los detalles finales
        for (DetalleCompra df : detallesFinales) {
            detalleDAO.guardar(df);
        }

        // 4. Guardar total de la compra y detalles finales en el objeto compra
        compra.setId_compra(idCompra);
        compra.setTotal(totalCompra);
        compra.setDetalles(detallesFinales);

        // 5. Actualizar compra en BD
        compraDAO.actualizar(compra);
    }

    // MÉTODOS DE CONSULTA
    public List<Compra> listarComprasPorUsuario(int idUsuario) {
        return compraDAO.listarComprasPorUsuario(idUsuario);
    }

    public List<DetalleCompra> listarDetallesPorCompra(int idCompra) {
        return detalleDAO.listarPorCompra(idCompra);
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
}

