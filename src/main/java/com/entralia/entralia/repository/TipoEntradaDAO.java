/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entralia.entralia.repository;

import com.entralia.entralia.model.TipoEntrada;
import java.util.List;
/**
 *
 * @author cristinamg210
 */
public interface TipoEntradaDAO {
    void guardar(TipoEntrada tipoEntrada);      
    void actualizar(TipoEntrada tipoEntrada); 
    void eliminar(int id);                      
    TipoEntrada obtenerPorId(int id);        
    List<TipoEntrada> listarTodos();   

    void actualizarStock(int idTipoEntrada, int nuevoStock);

    List<TipoEntrada> listarPorEvento(int idEvento);
}
