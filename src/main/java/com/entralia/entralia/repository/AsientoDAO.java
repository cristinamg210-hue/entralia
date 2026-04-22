package com.entralia.entralia.repository;

import com.entralia.entralia.model.Asiento;

public interface AsientoDAO {

    Asiento obtenerAsientoLibre(int idEvento);

    void marcarOcupado(int idAsiento);
}
