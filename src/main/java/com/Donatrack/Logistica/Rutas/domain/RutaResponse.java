package com.Donatrack.Logistica.Rutas.domain;

import com.Donatrack.Logistica.domain.Direccion;
import java.util.List;

public class RutaResponse {
    private List<Direccion> destinos;
    private List<String> pasos;

    // getters y setters
    public List<Direccion> getDestinos() { return destinos; }
    public void setDestinos(List<Direccion> destinos) { this.destinos = destinos; }

    public List<String> getPasos() { return pasos; }
    public void setPasos(List<String> pasos) { this.pasos = pasos; }
}
