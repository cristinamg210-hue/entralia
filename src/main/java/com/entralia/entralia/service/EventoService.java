/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entralia.entralia.service;

import com.entralia.entralia.model.Evento;
import com.entralia.entralia.repository.EventoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    // DAO que accede a la base de datos
    
    private final EventoDAO eventoDAO;

    @Autowired
    public EventoService(@Qualifier("eventoDAOJdbc") EventoDAO eventoDAO) {
        this.eventoDAO = eventoDAO;
    }

    public void guardarEvento(Evento evento) {
        eventoDAO.guardar(evento);
    }

    public void actualizarEvento(Evento evento) {
        eventoDAO.actualizar(evento);
    }

    public void eliminarEvento(int id) {
        eventoDAO.eliminar(id);
    }

    public Evento obtenerEventoPorId(int id) {
        return eventoDAO.obtenerPorId(id);
    }

    public List<Evento> listarEventos() {
        return eventoDAO.listarTodos();
    }
}
