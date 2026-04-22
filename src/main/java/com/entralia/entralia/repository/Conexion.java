package com.entralia.entralia.repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component   // permite inyectarla en los DAOJdbc
public class Conexion {
    private final JdbcTemplate jdbcTemplate;   // acceso a BD

    public Conexion(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;   // devuelve el objeto para ejecutar SQL
    }
}
