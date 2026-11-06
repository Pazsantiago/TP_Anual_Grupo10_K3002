package com.Donatrack.Logistica.domain;

public class Bien {
    private String nombre;
    private int cantidad;
    private String tipo;

    public Bien() {}

    public Bien(String nombre, int cantidad, String tipo) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre del bien es obligatorio");
        if (cantidad <= 0)
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.tipo = tipo;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}