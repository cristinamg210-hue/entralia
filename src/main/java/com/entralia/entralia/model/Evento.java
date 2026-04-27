/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entralia.entralia.model;

public class Evento {

    private int id_evento;
    private String nombre;
    private int aforo_total;
    private String sitio;
    private String fecha;
    private String imagen;
    private String descripcion;
    private boolean usa_asientos; // Indica si el evento usa asientos numerados

    // Campo calculado (NO va en la BD)
    private int stockTotal;

    public Evento() {}

    public Evento(int id_evento, String nombre, int aforo_total, String sitio, String fecha, String imagen, String descripcion) {
        this.id_evento = id_evento;
        this.nombre = nombre;
        this.aforo_total = aforo_total;
        this.sitio = sitio;
        this.fecha = fecha;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAforo_total() {
        return aforo_total;
    }

    public void setAforo_total(int aforo_total) {
        this.aforo_total = aforo_total;
    }

    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isUsa_asientos() {
        return usa_asientos;
    }

    public void setUsa_asientos(boolean usa_asientos) {
        this.usa_asientos = usa_asientos;
    }

    // GETTER y SETTER del stock total
    public int getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(int stockTotal) {
        this.stockTotal = stockTotal;
    }
}
