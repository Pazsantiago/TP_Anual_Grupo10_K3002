package com.Donatrack.Logistica.Rutas.domain;

import com.Donatrack.Logistica.domain.Direccion;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "rutas")
public class Ruta {

    public enum EstadoRuta { REGISTRADA, PLANIFICADA, EN_TRANSITO }

    @Id
    private String id;

    private List<Direccion> destinos = new ArrayList<>();
    private List<String> pasos = new ArrayList<>();
    private EstadoRuta estado = EstadoRuta.REGISTRADA;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public List<Direccion> getDestinos() { return destinos; }
    public void setDestinos(List<Direccion> destinos) { this.destinos = destinos; }
    public List<String> getPasos() { return pasos; }
    public void setPasos(List<String> pasos) { this.pasos = pasos; }
    public EstadoRuta getEstado() { return estado; }
    public void setEstado(EstadoRuta estado) { this.estado = estado; }
}