/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entralia.entralia.model;

/**
 *
 * @author cristinamg210
 */
public class TipoEntrada {
    private int id_tipo_entrada;   
    private int id_evento;         
    private double precio;         
    private String nombre; 
    private int stock;

    // Constructor vacío
    public TipoEntrada() {
    }

    // Constructor con parámetros
    public TipoEntrada(int id_tipo_entrada, int id_evento, double precio, String nombre, int stock) {
    this.id_tipo_entrada = id_tipo_entrada;
    this.id_evento = id_evento;
    this.precio = precio;
    this.nombre = nombre;
    this.stock = stock; 
}


    // Getters y setters
    public int getId_tipo_entrada() {
        return id_tipo_entrada;
    }

    public void setId_tipo_entrada(int id_tipo_entrada) {
        this.id_tipo_entrada = id_tipo_entrada;
    }

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
