package com.entralia.entralia.repository;

import com.entralia.entralia.model.DetalleCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("detalleCompraDAOJdbc")
public class DetalleCompraDAOJdbc implements DetalleCompraDAO {

    private final Conexion conexion; // Acceso al JdbcTemplate

    @Autowired
    private TipoEntradaDAO tipoEntradaDAO; // Para cargar el tipo de entrada completo

    @Autowired
    private EventoDAO eventoDAO; // Para cargar el evento completo

    @Autowired
    public DetalleCompraDAOJdbc(Conexion conexion) {
        this.conexion = conexion;
    }

    @Override
    public void guardar(DetalleCompra detalle) {

        // SQL para insertar un detalle de compra

        String sql = "INSERT INTO detalle_compra " +
                "(id_compra, id_evento, id_tipo_entrada, cantidad, precio_unitario, asiento) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        // Ejecuta el INSERT

        conexion.getJdbcTemplate().update(sql,
                detalle.getId_compra(),
                detalle.getId_evento(),
                detalle.getId_tipo_entrada(),
                detalle.getCantidad(),
                detalle.getPrecio_unitario(),
                detalle.getAsiento()  // Guarda el asiento si existe
        );
    }

    @Override
    public void actualizar(DetalleCompra detalle) {

        // SQL para actualizar un detalle

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

        // SQL para obtener un detalle por ID

        String sql = "SELECT * FROM detalle_compra WHERE id_detalle = ?";

        return conexion.getJdbcTemplate().queryForObject(sql, (rs, rowNum) -> {

            // Construye el objeto DetalleCompra

            DetalleCompra d = new DetalleCompra(
                    rs.getInt("id_detalle"),
                    rs.getInt("id_compra"),
                    rs.getInt("id_evento"),
                    rs.getInt("id_tipo_entrada"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio_unitario")
            );

            d.setAsiento(rs.getString("asiento")); // Asiento asignado

            // Carga objetos completos para las vistas

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

            // Carga datos completos

            d.setTipoEntrada(tipoEntradaDAO.obtenerPorId(d.getId_tipo_entrada()));
            d.setEvento(eventoDAO.obtenerPorId(d.getId_evento()));

            return d;
        });
    }

    @Override
    public List<DetalleCompra> listarPorCompra(int idCompra) {

        // SQL para obtener todos los detalles de una compra

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

            // Carga objetos completos para mostrar en el ticket
            
            d.setTipoEntrada(tipoEntradaDAO.obtenerPorId(d.getId_tipo_entrada()));
            d.setEvento(eventoDAO.obtenerPorId(d.getId_evento()));

            return d;

        }, idCompra);
    }
}

