package com.entralia.entralia.model;

public class DetalleCompra {

    private int id_detalle;
    private int id_compra;
    private int id_evento;
    private int id_tipo_entrada;
    private int cantidad;
    private double precio_unitario;
    private String asiento;

    // Referencias para las vistas
    private Evento evento;
    private TipoEntrada tipoEntrada;

    public DetalleCompra() {}

    public DetalleCompra(int id_detalle, int id_compra, int id_evento,
                         int id_tipo_entrada, int cantidad, double precio_unitario) {
        this.id_detalle = id_detalle;
        this.id_compra = id_compra;
        this.id_evento = id_evento;
        this.id_tipo_entrada = id_tipo_entrada;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
    }

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public int getId_tipo_entrada() {
        return id_tipo_entrada;
    }

    public void setId_tipo_entrada(int id_tipo_entrada) {
        this.id_tipo_entrada = id_tipo_entrada;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    // total del detalle
    public double getPrecioTotal() {
        return cantidad * precio_unitario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public TipoEntrada getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(TipoEntrada tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

}


