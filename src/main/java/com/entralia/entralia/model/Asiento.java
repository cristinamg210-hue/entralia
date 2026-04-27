package com.entralia.entralia.model;

public class Asiento {

    private int id_asiento;
    private int id_evento;
    private String numero;
    private boolean ocupado; // Indica si el asiento está ocupado o libre

    public Asiento() {} // Constructor vacío obligatorio para Spring

    public int getId_asiento() {
        return id_asiento;
    }

    public void setId_asiento(int id_asiento) {
        this.id_asiento = id_asiento;
    }

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
}
