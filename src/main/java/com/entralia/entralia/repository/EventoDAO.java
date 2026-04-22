/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entralia.entralia.repository;
import com.entralia.entralia.model.Evento;
import java.util.List;
/**
 *
 * @author cristinamg210
 */
public interface EventoDAO {
    void guardar(Evento evento);        
    void actualizar(Evento evento);        
    void eliminar(int id);                 
    Evento obtenerPorId(int id);           
    List<Evento> listarTodos();   
}
