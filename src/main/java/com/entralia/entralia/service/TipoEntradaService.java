/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entralia.entralia.service;

import com.entralia.entralia.model.TipoEntrada;
import com.entralia.entralia.repository.TipoEntradaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoEntradaService {

    private final TipoEntradaDAO tipoEntradaDAO;

    @Autowired
    public TipoEntradaService(@Qualifier("tipoEntradaDAOJdbc") TipoEntradaDAO tipoEntradaDAO) {
        this.tipoEntradaDAO = tipoEntradaDAO;
    }

    public void guardarTipoEntrada(TipoEntrada tipoEntrada) {
        tipoEntradaDAO.guardar(tipoEntrada);
    }

    public void actualizarTipoEntrada(TipoEntrada tipoEntrada) {
        tipoEntradaDAO.actualizar(tipoEntrada);
    }

    public void eliminarTipoEntrada(int id) {
        tipoEntradaDAO.eliminar(id);
    }

    public TipoEntrada obtenerTipoEntradaPorId(int id) {
        return tipoEntradaDAO.obtenerPorId(id);
    }

    public List<TipoEntrada> listarTipoEntradas() {
        return tipoEntradaDAO.listarTodos();
    }

    //  actualizar stock
    public void actualizarStock(int idTipoEntrada, int nuevoStock) {
        tipoEntradaDAO.actualizarStock(idTipoEntrada, nuevoStock);
    }

    // listar tipos por evento
    public List<TipoEntrada> listarPorEvento(int idEvento) {
        return tipoEntradaDAO.listarPorEvento(idEvento);
    }
}

