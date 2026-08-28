package com.Donatrack.Logistica.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ElementCollection;
import java.util.List;

@Entity
public class PlanRuta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<Direccion> direcciones;

    private Double distanciaTotal;
    private String tiempoEstimado;

    public PlanRuta() {
    }

    public PlanRuta(Long id, List<Direccion> direcciones, Double distanciaTotal, String tiempoEstimado) {
        this.id = id;
        this.direcciones = direcciones;
        this.distanciaTotal = distanciaTotal;
        this.tiempoEstimado = tiempoEstimado;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public List<Direccion> getDirecciones() { return direcciones; }
    public void setDirecciones(List<Direccion> direcciones) { this.direcciones = direcciones; }

    public Double getDistanciaTotal() { return distanciaTotal; }
    public void setDistanciaTotal(Double distanciaTotal) { this.distanciaTotal = distanciaTotal; }

    public String getTiempoEstimado() { return tiempoEstimado; }
    public void setTiempoEstimado(String tiempoEstimado) { this.tiempoEstimado = tiempoEstimado; }
}
