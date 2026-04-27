package com.entralia.entralia.repository;

import com.entralia.entralia.model.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("eventoDAOJdbc")
public class EventoDAOJdbc implements EventoDAO {

    private final Conexion conexion; // Acceso al JdbcTemplate
 
    @Autowired
    public EventoDAOJdbc(Conexion conexion) {
        this.conexion = conexion;
    }

    @Override
    public void guardar(Evento evento) {

        // SQL para insertar un nuevo evento

        String sql = "INSERT INTO evento (nombre, aforo_total, sitio, fecha, imagen, descripcion, usa_asientos) VALUES (?, ?, ?, ?, ?, ?, ?)";
        conexion.getJdbcTemplate().update(sql,
                evento.getNombre(),
                evento.getAforo_total(),
                evento.getSitio(),
                evento.getFecha(),
                evento.getImagen(),
                evento.getDescripcion(),
                evento.isUsa_asientos() // TRUE/FALSE según si usa asientos numerados
        );
    }

    @Override
    public void actualizar(Evento evento) {

        // SQL para actualizar un evento existente

        String sql = "UPDATE evento SET nombre = ?, aforo_total = ?, sitio = ?, fecha = ?, imagen = ?, descripcion = ?, usa_asientos = ? WHERE id_evento = ?";
        conexion.getJdbcTemplate().update(sql,
                evento.getNombre(),
                evento.getAforo_total(),
                evento.getSitio(),
                evento.getFecha(),
                evento.getImagen(),
                evento.getDescripcion(),
                evento.isUsa_asientos(),
                evento.getId_evento()
        );
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM evento WHERE id_evento = ?";
        conexion.getJdbcTemplate().update(sql, id);
    }

    @Override
    public Evento obtenerPorId(int id) {

        // SQL para obtener un evento por su ID

        String sql = "SELECT * FROM evento WHERE id_evento = ?";
        return conexion.getJdbcTemplate().queryForObject(sql, (rs, rowNum) -> {

            // Construye el objeto Evento con los datos de la BD

            Evento e = new Evento(
                    rs.getInt("id_evento"),
                    rs.getString("nombre"),
                    rs.getInt("aforo_total"),
                    rs.getString("sitio"),
                    rs.getString("fecha"),
                    rs.getString("imagen"),
                    rs.getString("descripcion")
            );

            // Campo booleano: si usa asientos numerados

            e.setUsa_asientos(rs.getBoolean("usa_asientos"));
            return e;
        }, id);
    }

    @Override
    public List<Evento> listarTodos() {

        // SQL para listar todos los eventos
        
        String sql = "SELECT * FROM evento";
        return conexion.getJdbcTemplate().query(sql, (rs, rowNum) -> {
            Evento e = new Evento(
                    rs.getInt("id_evento"),
                    rs.getString("nombre"),
                    rs.getInt("aforo_total"),
                    rs.getString("sitio"),
                    rs.getString("fecha"),
                    rs.getString("imagen"),
                    rs.getString("descripcion")
            );
            e.setUsa_asientos(rs.getBoolean("usa_asientos"));
            return e;
        });
    }
}

