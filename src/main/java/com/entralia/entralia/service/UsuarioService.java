/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entralia.entralia.service;
import com.entralia.entralia.model.Usuario;
import com.entralia.entralia.repository.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 *
 * @author cristinamg210
 */

@Service
public class UsuarioService {

    @Autowired
    @Qualifier("usuarioDAOJdbc")
    private UsuarioDAO usuarioDAO;  

    public void guardarUsuario(Usuario usuario) {
        usuarioDAO.guardar(usuario);
    }

    public void actualizarUsuario(Usuario usuario) {
        usuarioDAO.actualizar(usuario);
    }

    public void eliminarUsuario(int id) {
        usuarioDAO.eliminar(id);
    }

    public Usuario obtenerUsuarioPorId(int id) {
        return usuarioDAO.obtenerPorId(id);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listarTodos();
    }

    public Usuario buscarPorEmail(String email) {
    return usuarioDAO.buscarPorEmail(email);
}

}
