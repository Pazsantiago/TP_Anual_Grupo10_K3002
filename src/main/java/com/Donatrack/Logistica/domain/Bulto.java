package com.Donatrack.Logistica.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Bulto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double peso;
    private double volumen;
    private double altura;

    @ManyToOne
    private Camion camion;

    public Bulto() {
    }

    public Bulto(Long id, double peso, double volumen, double altura) {
        setPeso(peso);
        if (volumen < 0 || altura < 0) {
            throw new IllegalArgumentException("El volumen y la altura no pueden ser negativos");
        }
        this.id = id;
        this.volumen = volumen;
        this.altura = altura;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) {
        if (peso <= 0) throw new IllegalArgumentException("El peso debe ser mayor a cero");
        this.peso = peso;
    }
    public double getVolumen() { return volumen; }
    public void setVolumen(double volumen) { this.volumen = volumen; }
    public double getAltura() { return altura; }
    public void setAltura(double altura) { this.altura = altura; }
    public Camion getCamion() { return camion; }
    public void setCamion(Camion camion) { this.camion = camion; }
}
