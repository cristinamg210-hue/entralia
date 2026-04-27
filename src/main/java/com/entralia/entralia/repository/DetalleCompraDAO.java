/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entralia.entralia.repository;
import com.entralia.entralia.model.DetalleCompra;
import java.util.List;
/**
 *
 * @author cristinamg210
 */
public interface DetalleCompraDAO {
    void guardar(DetalleCompra detalle);      
    void actualizar(DetalleCompra detalle);  
    void eliminar(int id);                      
    DetalleCompra obtenerPorId(int id);       
    List<DetalleCompra> listarTodos(); // Lista todos los detalles de compra de la BD
    List<DetalleCompra> listarPorCompra(int idCompra);
}
