package com.entralia.entralia.repository;

import com.entralia.entralia.model.Asiento;

public interface AsientoDAO {

    // Devuelve un asiento libre (ocupado = false) para un evento concreto
    Asiento obtenerAsientoLibre(int idEvento);

    // Marca un asiento como ocupado en la base de datos
    void marcarOcupado(int idAsiento);
}
