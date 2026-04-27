package com.entralia.entralia.repository;

import com.entralia.entralia.model.Compra;
import com.entralia.entralia.model.DetalleCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository("compraDAOJdbc")
public class CompraDAOJdbc implements CompraDAO {

    private final Conexion conexion;

    @Autowired
    private DetalleCompraDAO detalleCompraDAO; // Para cargar los detalles de cada compra

    @Autowired
    public CompraDAOJdbc(Conexion conexion) {
        this.conexion = conexion;
    }

    @Override
    public int guardar(Compra compra) {

        // SQL: solo guarda el id_usuario, fecha_compra se genera sola (DEFAULT CURRENT_TIMESTAMP)

        String sql = "INSERT INTO compra (id_usuario) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder(); // Para obtener el ID generado

        conexion.getJdbcTemplate().update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, compra.getId_usuario());
            return ps;
        }, keyHolder);

        // Devuelve el ID autogenerado de la compra

        return keyHolder.getKey().intValue();
    }

    @Override
    public void actualizar(Compra compra) {
        String sql = "UPDATE compra SET id_usuario = ? WHERE id_compra = ?";
        conexion.getJdbcTemplate().update(sql,
                compra.getId_usuario(),
                compra.getId_compra());
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM compra WHERE id_compra = ?";
        conexion.getJdbcTemplate().update(sql, id);
    }

    @Override
public Compra obtenerPorId(int id) {

    // Obtiene la compra básica

    String sql = "SELECT * FROM compra WHERE id_compra = ?";

    Compra compra = conexion.getJdbcTemplate().queryForObject(sql, (rs, rowNum) ->
            new Compra(
                    rs.getInt("id_compra"),
                    rs.getInt("id_usuario"),
                    rs.getString("fecha_compra")
            ), id);

    // Carga los detalles asociados

    List<DetalleCompra> detalles = detalleCompraDAO.listarPorCompra(id);
    compra.setDetalles(detalles);

    // Calcula el total sumando cada detalle

    double total = detalles.stream()
            .mapToDouble(d -> d.getCantidad() * d.getPrecio_unitario())
            .sum();
    compra.setTotal(total);

    return compra;
}

@Override
public List<Compra> listarTodos() {

    // Lista todas las compras

    String sql = "SELECT * FROM compra";

    return conexion.getJdbcTemplate().query(sql, (rs, rowNum) -> {

        Compra compra = new Compra(
                rs.getInt("id_compra"),
                rs.getInt("id_usuario"),
                rs.getString("fecha_compra")
        );

        // Carga los detalles de cada compra

        List<DetalleCompra> detalles = detalleCompraDAO.listarPorCompra(compra.getId_compra());
        compra.setDetalles(detalles);

        // Calcula el total

        double total = detalles.stream()
                .mapToDouble(d -> d.getCantidad() * d.getPrecio_unitario())
                .sum();
        compra.setTotal(total);

        return compra;
    });
}

@Override
public List<Compra> listarComprasPorUsuario(int idUsuario) {

    // Lista compras del usuario ordenadas por fecha descendente

    String sql = "SELECT * FROM compra WHERE id_usuario = ? ORDER BY fecha_compra DESC";

    return conexion.getJdbcTemplate().query(sql, (rs, rowNum) -> {

        Compra compra = new Compra(
                rs.getInt("id_compra"),
                rs.getInt("id_usuario"),
                rs.getString("fecha_compra")
        );

        // Carga los detalles de la compra

        List<DetalleCompra> detalles = detalleCompraDAO.listarPorCompra(compra.getId_compra());
        compra.setDetalles(detalles);

        // Calcula el total
        
        double total = detalles.stream()
                .mapToDouble(d -> d.getCantidad() * d.getPrecio_unitario())
                .sum();
        compra.setTotal(total);

        return compra;

    }, idUsuario);
}

}

