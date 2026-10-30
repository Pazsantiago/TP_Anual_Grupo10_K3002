package com.Donatrack.Logistica.domain;

/*import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List; */


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "camiones")
public class Camion {
    @Id
    private String id;

    @Indexed(unique = true)
    private String patente;

    private double capacidadCarga;      // kg
    private double capacidadVolumenM3;  // m³
    private double alturaM;
    private double cargaActual;
    private double volumenActual;
    private boolean disponible = true;
    private UbicacionCamion ultimaUbicacion;
    private List<Bulto> bultosAsignados = new ArrayList<>();

    public Camion() {}

    public Camion(String patente, double capacidadCarga, double capacidadVolumenM3, double alturaM) {
        this.patente = patente;
        this.capacidadCarga = capacidadCarga;
        this.capacidadVolumenM3 = capacidadVolumenM3;
        this.alturaM = alturaM;
    }

    public boolean asignar(Bulto bulto) {
        if (cargaActual + bulto.getPeso() > capacidadCarga) return false;
        if (volumenActual + bulto.getVolumen() > capacidadVolumenM3) return false;
        if (bulto.getAltura() > alturaM) return false;
        bultosAsignados.add(bulto);
        cargaActual += bulto.getPeso();
        volumenActual += bulto.getVolumen();
        return true;
    }

    public void actualizarUbicacion(double lat, double lng) {
        this.ultimaUbicacion = new UbicacionCamion(lat, lng);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }
    public double getCapacidadCarga() { return capacidadCarga; }
    public void setCapacidadCarga(double c) { this.capacidadCarga = c; }
    public double getCapacidadVolumenM3() { return capacidadVolumenM3; }
    public void setCapacidadVolumenM3(double c) { this.capacidadVolumenM3 = c; }
    public double getAlturaM() { return alturaM; }
    public void setAlturaM(double a) { this.alturaM = a; }
    public double getCargaActual() { return cargaActual; }
    public double getVolumenActual() { return volumenActual; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean d) { this.disponible = d; }
    public UbicacionCamion getUltimaUbicacion() { return ultimaUbicacion; }
    public List<Bulto> getBultosAsignados() { return bultosAsignados; }
}