/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entralia.entralia.service;

import com.entralia.entralia.model.DetalleCompra;
import com.entralia.entralia.repository.DetalleCompraDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 *
 * @author cristinamg210
 */

@Service
public class DetalleCompraService {

    //DAO que accede a la BD
    
    private final DetalleCompraDAO detalleCompraDAO;

    @Autowired
    public DetalleCompraService(@Qualifier("detalleCompraDAOJdbc") DetalleCompraDAO detalleCompraDAO) {
        this.detalleCompraDAO = detalleCompraDAO;
    }

    public void guardarDetalleCompra(DetalleCompra detalle) {
        detalleCompraDAO.guardar(detalle);
    }

    public void actualizarDetalleCompra(DetalleCompra detalle) {
        detalleCompraDAO.actualizar(detalle);
    }

    public void eliminarDetalleCompra(int id) {
        detalleCompraDAO.eliminar(id);
    }

    public DetalleCompra obtenerDetalleCompraPorId(int id) {
        return detalleCompraDAO.obtenerPorId(id);
    }

    public List<DetalleCompra> listarDetalleCompras() {
        return detalleCompraDAO.listarTodos();
    }

    public List<DetalleCompra> listarPorCompra(int idCompra) {
        return detalleCompraDAO.listarPorCompra(idCompra);
    }

}
