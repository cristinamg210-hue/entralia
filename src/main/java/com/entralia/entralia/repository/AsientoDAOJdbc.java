package com.entralia.entralia.repository;

import com.entralia.entralia.model.Asiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("asientoDAOJdbc") 
public class AsientoDAOJdbc implements AsientoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate; // Para ejecutar consultas SQL

    @Override
    public Asiento obtenerAsientoLibre(int idEvento) {

        // SQL: busca un asiento libre (ocupado = FALSE) del evento

        String sql = "SELECT * FROM asiento WHERE id_evento = ? AND ocupado = FALSE LIMIT 1";

        // Ejecuta la consulta y convierte el resultado en un objeto Asiento

        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                Asiento a = new Asiento();
                a.setId_asiento(rs.getInt("id_asiento"));
                a.setId_evento(rs.getInt("id_evento"));
                a.setNumero(rs.getString("numero"));
                a.setOcupado(rs.getBoolean("ocupado"));
                return a; // Devuelve el asiento libre encontrado
            }
            return null; // Si no hay asientos libres, devuelve null
        }, idEvento);
    }

    @Override
    public void marcarOcupado(int idAsiento) {

        // SQL: marca el asiento como ocupado

        String sql = "UPDATE asiento SET ocupado = TRUE WHERE id_asiento = ?";
        jdbcTemplate.update(sql, idAsiento); // Ejecuta la actualización
    }
}
