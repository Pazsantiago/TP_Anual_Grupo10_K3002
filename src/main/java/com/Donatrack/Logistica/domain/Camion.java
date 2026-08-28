package com.Donatrack.Logistica.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Camion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String patente;
    private double capacidadCarga;
    private double cargaActual;
    @OneToMany(mappedBy = "camion", cascade = CascadeType.ALL)
    private final List<Bulto> bultosAsignados = new ArrayList<>();

    public Camion() {
    }

    public Camion(String patente, double capacidadCarga) {
        this.patente = patente;
        this.capacidadCarga = capacidadCarga;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }
    public double getCapacidadCarga() { return capacidadCarga; }
    public void setCapacidadCarga(double capacidadCarga) { this.capacidadCarga = capacidadCarga; }
    public double getCargaActual() { return cargaActual; }
    public List<Bulto> getBultosAsignados() { return bultosAsignados; }

    public boolean asignar(Bulto bulto) {
        if (cargaActual + bulto.getPeso() > capacidadCarga) return false;
        bultosAsignados.add(bulto);
        bulto.setCamion(this);
        cargaActual += bulto.getPeso();
        return true;
    }
}
