package com.entralia.entralia.repository;

import com.entralia.entralia.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("usuarioDAOJdbc")
public class UsuarioDAOJdbc implements UsuarioDAO {

    private final Conexion conexion;

    @Autowired
    public UsuarioDAOJdbc(Conexion conexion) {
        this.conexion = conexion;
    }

    @Override
    public void guardar(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, email, rol) VALUES (?, ?, ?)";
        conexion.getJdbcTemplate().update(sql, usuario.getNombre(), usuario.getEmail(), usuario.getRol());
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre = ?, email = ?, rol = ? WHERE id_usuario = ?";
        conexion.getJdbcTemplate().update(sql, usuario.getNombre(), usuario.getEmail(), usuario.getRol(), usuario.getId_usuario());
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        conexion.getJdbcTemplate().update(sql, id);
    }

    @Override
    public Usuario obtenerPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
        return conexion.getJdbcTemplate().queryForObject(sql, (rs, rowNum) ->
                new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("rol")
                ), id);
    }

    @Override
    public List<Usuario> listarTodos() {
        String sql = "SELECT * FROM usuario";
        return conexion.getJdbcTemplate().query(sql, (rs, rowNum) ->
                new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("rol")
                ));
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        List<Usuario> lista = conexion.getJdbcTemplate().query(sql, (rs, rowNum) ->
                new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("rol")
                ), email);

        return lista.isEmpty() ? null : lista.get(0);
    }


}
