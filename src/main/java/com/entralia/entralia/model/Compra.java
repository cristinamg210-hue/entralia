package com.entralia.entralia.model;

import java.util.List;

public class Compra {
    private int id_compra;
    private int id_usuario;
    private String fecha_compra;

    private List<DetalleCompra> detalles;

    // total guardado en BD
    private double total;

    public Compra() {}

    public Compra(int id_compra, int id_usuario, String fecha_compra) {
        this.id_compra = id_compra;
        this.id_usuario = id_usuario;
        this.fecha_compra = fecha_compra;
    }

    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public List<DetalleCompra> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCompra> detalles) {
        this.detalles = detalles;
    }

    // total guardado
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    // total calculado dinámicamente (lo puedes seguir usando en vistas)
    public double getTotalCompra() {
        if (detalles == null) return 0;
        return detalles.stream()
                .mapToDouble(DetalleCompra::getPrecioTotal)
                .sum();
    }

    public String getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(String fecha_compra) {
        this.fecha_compra = fecha_compra;
    }
}

