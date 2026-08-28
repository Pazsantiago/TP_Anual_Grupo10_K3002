package com.Donatrack.Logistica.Rutas.domain;

import com.Donatrack.Logistica.domain.Direccion;
import java.util.ArrayList;
import java.util.List;

public class Ruta {
    private List<Direccion> destinos = new ArrayList<>();
    private List<String> pasos = new ArrayList<>();
    private String estado;

    public Ruta() {
    }

    public Ruta(List<Direccion> destinos) {
        this.destinos = destinos;
    }

    public List<Direccion> getDestinos() { return destinos; }
    public void setDestinos(List<Direccion> destinos) { this.destinos = destinos; }
    public List<String> getPasos() { return pasos; }
    public void setPasos(List<String> pasos) { this.pasos = pasos; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}

