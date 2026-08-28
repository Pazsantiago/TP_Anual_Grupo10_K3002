package com.Donatrack.Logistica.Rutas.domain;

import com.Donatrack.Logistica.domain.Direccion;

public class Destino {
    private Direccion direccion;
    private String distancia;
    private String duracion;

    public Destino() {
    }

    // Constructor
    public Destino(Direccion direccion, String distancia, String duracion) {
        this.direccion = direccion;
        this.distancia = distancia;
        this.duracion = duracion;
    }

    // Getters y Setters
    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
}
