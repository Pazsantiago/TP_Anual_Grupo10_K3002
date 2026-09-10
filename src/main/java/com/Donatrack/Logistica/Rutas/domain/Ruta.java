package com.Donatrack.Logistica.Rutas.domain;

import com.Donatrack.Logistica.domain.Direccion;
import java.util.List;

public class Ruta {

    public enum EstadoRuta {
        REGISTRADA,
        PLANIFICADA,
        EN_TRANSITO
    }

    private List<Direccion> destinos;
    private List<String> pasos;
    private EstadoRuta estado;

    // getters y setters
    public List<Direccion> getDestinos() { return destinos; }
    public void setDestinos(List<Direccion> destinos) { this.destinos = destinos; }

    public List<String> getPasos() { return pasos; }
    public void setPasos(List<String> pasos) { this.pasos = pasos; }

    public EstadoRuta getEstado() { return estado; }
    public void setEstado(EstadoRuta estado) { this.estado = estado; }
}
