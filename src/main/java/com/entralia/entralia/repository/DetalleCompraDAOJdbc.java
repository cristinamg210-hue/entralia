package com.entralia.entralia.repository;

import com.entralia.entralia.model.DetalleCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("detalleCompraDAOJdbc")
public class DetalleCompraDAOJdbc implements DetalleCompraDAO {

    private final Conexion conexion;

    @Autowired
    private TipoEntradaDAO tipoEntradaDAO;

    @Autowired
    private EventoDAO eventoDAO;

    @Autowired
    public DetalleCompraDAOJdbc(Conexion conexion) {
        this.conexion = conexion;
    }

    @Override
    public void guardar(DetalleCompra detalle) {

        String sql = "INSERT INTO detalle_compra " +
                "(id_compra, id_evento, id_tipo_entrada, cantidad, precio_unitario, asiento) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        conexion.getJdbcTemplate().update(sql,
                detalle.getId_compra(),
                detalle.getId_evento(),
                detalle.getId_tipo_entrada(),
                detalle.getCantidad(),
                detalle.getPrecio_unitario(),
                detalle.getAsiento() 
        );
    }

    @Override
    public void actualizar(DetalleCompra detalle) {

        String sql = "UPDATE detalle_compra SET " +
                "id_compra = ?, id_evento = ?, id_tipo_entrada = ?, cantidad = ?, precio_unitario = ?, asiento = ? " +
                "WHERE id_detalle = ?";

        conexion.getJdbcTemplate().update(sql,
                detalle.getId_compra(),
                detalle.getId_evento(),
                detalle.getId_tipo_entrada(),
                detalle.getCantidad(),
                detalle.getPrecio_unitario(),
                detalle.getAsiento(), 
                detalle.getId_detalle()
        );
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM detalle_compra WHERE id_detalle = ?";
        conexion.getJdbcTemplate().update(sql, id);
    }

    @Override
    public DetalleCompra obtenerPorId(int id) {

        String sql = "SELECT * FROM detalle_compra WHERE id_detalle = ?";

        return conexion.getJdbcTemplate().queryForObject(sql, (rs, rowNum) -> {

            DetalleCompra d = new DetalleCompra(
                    rs.getInt("id_detalle"),
                    rs.getInt("id_compra"),
                    rs.getInt("id_evento"),
                    rs.getInt("id_tipo_entrada"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio_unitario")
            );

            d.setAsiento(rs.getString("asiento"));

            d.setTipoEntrada(tipoEntradaDAO.obtenerPorId(d.getId_tipo_entrada()));
            d.setEvento(eventoDAO.obtenerPorId(d.getId_evento()));

            return d;

        }, id);
    }

    @Override
    public List<DetalleCompra> listarTodos() {

        String sql = "SELECT * FROM detalle_compra";

        return conexion.getJdbcTemplate().query(sql, (rs, rowNum) -> {

            DetalleCompra d = new DetalleCompra(
                    rs.getInt("id_detalle"),
                    rs.getInt("id_compra"),
                    rs.getInt("id_evento"),
                    rs.getInt("id_tipo_entrada"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio_unitario")
            );

            d.setAsiento(rs.getString("asiento")); 

            d.setTipoEntrada(tipoEntradaDAO.obtenerPorId(d.getId_tipo_entrada()));
            d.setEvento(eventoDAO.obtenerPorId(d.getId_evento()));

            return d;
        });
    }

    @Override
    public List<DetalleCompra> listarPorCompra(int idCompra) {

        String sql = "SELECT * FROM detalle_compra WHERE id_compra = ?";

        return conexion.getJdbcTemplate().query(sql, (rs, rowNum) -> {

            DetalleCompra d = new DetalleCompra(
                    rs.getInt("id_detalle"),
                    rs.getInt("id_compra"),
                    rs.getInt("id_evento"),
                    rs.getInt("id_tipo_entrada"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio_unitario")
            );

            d.setAsiento(rs.getString("asiento")); 

            d.setTipoEntrada(tipoEntradaDAO.obtenerPorId(d.getId_tipo_entrada()));
            d.setEvento(eventoDAO.obtenerPorId(d.getId_evento()));

            return d;

        }, idCompra);
    }
}

