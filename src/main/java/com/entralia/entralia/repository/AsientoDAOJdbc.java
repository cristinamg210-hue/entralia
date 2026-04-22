package com.entralia.entralia.repository;

import com.entralia.entralia.model.Asiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("asientoDAOJdbc")
public class AsientoDAOJdbc implements AsientoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Asiento obtenerAsientoLibre(int idEvento) {
        String sql = "SELECT * FROM asiento WHERE id_evento = ? AND ocupado = FALSE LIMIT 1";

        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                Asiento a = new Asiento();
                a.setId_asiento(rs.getInt("id_asiento"));
                a.setId_evento(rs.getInt("id_evento"));
                a.setNumero(rs.getString("numero"));
                a.setOcupado(rs.getBoolean("ocupado"));
                return a;
            }
            return null;
        }, idEvento);
    }

    @Override
    public void marcarOcupado(int idAsiento) {
        String sql = "UPDATE asiento SET ocupado = TRUE WHERE id_asiento = ?";
        jdbcTemplate.update(sql, idAsiento);
    }
}
