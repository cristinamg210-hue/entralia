/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entralia.entralia.repository;
import com.entralia.entralia.model.Usuario;
import java.util.List;

/**
 *
 * @author cristinamg210
 */
public interface UsuarioDAO {
    void guardar(Usuario usuario);       
    void actualizar(Usuario usuario);       
    void eliminar(int id);                 
    Usuario obtenerPorId(int id);          
    List<Usuario> listarTodos(); 
    Usuario buscarPorEmail(String email);
}
