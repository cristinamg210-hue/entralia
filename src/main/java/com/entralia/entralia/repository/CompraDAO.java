/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entralia.entralia.repository;
import com.entralia.entralia.model.Compra;
import java.util.List;
/**
 *
 * @author cristinamg210
 */
public interface CompraDAO {
    int guardar(Compra compra);           
    void actualizar(Compra compra);    
    void eliminar(int id);                 
    Compra obtenerPorId(int id);           
    List<Compra> listarTodos(); // Lista todas las compras de la BD
    List<Compra> listarComprasPorUsuario(int idUsuario);     
}
