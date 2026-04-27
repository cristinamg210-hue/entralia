package com.entralia.entralia.repository;

import com.entralia.entralia.model.TipoEntrada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("tipoEntradaDAOJdbc")
public class TipoEntradaDAOJdbc implements TipoEntradaDAO {

    private final Conexion conexion;

    @Autowired
    public TipoEntradaDAOJdbc(Conexion conexion) {
        this.conexion = conexion;
    }

    @Override
    public void guardar(TipoEntrada tipo) {

        // SQL para insertar un nuevo tipo de entrada

        String sql = "INSERT INTO tipo_entrada (id_evento, precio, nombre, stock) VALUES (?, ?, ?, ?)";
        conexion.getJdbcTemplate().update(sql,
                tipo.getId_evento(),
                tipo.getPrecio(),
                tipo.getNombre(),
                tipo.getStock()  // stock inicial
        );
    }

    @Override
    public void actualizar(TipoEntrada tipo) {

        // SQL para actualizar un tipo de entrada existente

        String sql = "UPDATE tipo_entrada SET id_evento = ?, precio = ?, nombre = ?, stock = ? WHERE id_tipo_entrada = ?";
        conexion.getJdbcTemplate().update(sql,
                tipo.getId_evento(),
                tipo.getPrecio(),
                tipo.getNombre(),
                tipo.getStock(),  
                tipo.getId_tipo_entrada()
        );
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM tipo_entrada WHERE id_tipo_entrada = ?";
        conexion.getJdbcTemplate().update(sql, id);
    }

    @Override
    public TipoEntrada obtenerPorId(int id) {

        // SQL para obtener un tipo de entrada por su ID

        String sql = "SELECT * FROM tipo_entrada WHERE id_tipo_entrada = ?";
        return conexion.getJdbcTemplate().queryForObject(sql, (rs, rowNum) ->
                new TipoEntrada(
                        rs.getInt("id_tipo_entrada"),
                        rs.getInt("id_evento"),
                        rs.getDouble("precio"),
                        rs.getString("nombre"),
                        rs.getInt("stock") 
                ), id);
    }

    @Override
    public List<TipoEntrada> listarTodos() {

        // SQL para listar todos los tipos de entrada

        String sql = "SELECT * FROM tipo_entrada";
        return conexion.getJdbcTemplate().query(sql, (rs, rowNum) ->
                new TipoEntrada(
                        rs.getInt("id_tipo_entrada"),
                        rs.getInt("id_evento"),
                        rs.getDouble("precio"),
                        rs.getString("nombre"),
                        rs.getInt("stock")  
                ));
    }

    // actualizar stock
    @Override
    public void actualizarStock(int idTipoEntrada, int nuevoStock) {

        // SQL para actualizar el stock disponible

        String sql = "UPDATE tipo_entrada SET stock = ? WHERE id_tipo_entrada = ?";
        conexion.getJdbcTemplate().update(sql, nuevoStock, idTipoEntrada);
    }

    // listar tipos por evento
    @Override
    public List<TipoEntrada> listarPorEvento(int idEvento) {

        // SQL para listar los tipos de entrada de un evento concreto
        
        String sql = "SELECT * FROM tipo_entrada WHERE id_evento = ?";
        return conexion.getJdbcTemplate().query(sql, (rs, rowNum) ->
                new TipoEntrada(
                        rs.getInt("id_tipo_entrada"),
                        rs.getInt("id_evento"),
                        rs.getDouble("precio"),
                        rs.getString("nombre"),
                        rs.getInt("stock")  
                ), idEvento);
    }
}
