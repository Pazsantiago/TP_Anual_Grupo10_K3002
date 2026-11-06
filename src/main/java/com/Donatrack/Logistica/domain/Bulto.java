package com.Donatrack.Logistica.domain;

import java.util.ArrayList;
import java.util.List;

public class Bulto {
    private String id;
    private double peso;
    private double volumen;
    private double altura;
    private List<Bien> bienes = new ArrayList<>();

    public Bulto() {}

    public Bulto(String id, double peso, double volumen, double altura) {
        setPeso(peso);
        if (volumen < 0 || altura < 0)
            throw new IllegalArgumentException("El volumen y la altura no pueden ser negativos");
        this.id = id;
        this.volumen = volumen;
        this.altura = altura;
    }

    public Bulto(double peso, double volumen, double altura) {
        this(null, peso, volumen, altura);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) {
        if (peso <= 0) throw new IllegalArgumentException("El peso debe ser mayor a cero");
        this.peso = peso;
    }
    public double getVolumen() { return volumen; }
    public void setVolumen(double volumen) { this.volumen = volumen; }
    public double getAltura() { return altura; }
    public void setAltura(double altura) { this.altura = altura; }
    public List<Bien> getBienes() { return bienes; }
    public void setBienes(List<Bien> bienes) { this.bienes = bienes; }
}