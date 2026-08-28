package com.Donatrack.Logistica.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDonacion;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Bulto> bultos;

    @Embedded
    private Direccion direccion;

    private String destinatario;

    @Enumerated(EnumType.STRING)
    private EstadoEntrega estadoDonacion;

    public Donacion() {
    }

    public Donacion(Long idDonacion, List<Bulto> bultos, Direccion direccion, String destinatario, EstadoEntrega estadoDonacion) {
        this.idDonacion = idDonacion;
        this.bultos = bultos;
        this.direccion = direccion;
        this.destinatario = destinatario;
        this.estadoDonacion = estadoDonacion;
    }

    // Getters y Setters
    public Long getIdDonacion() { return idDonacion; }
    public void setIdDonacion(Long idDonacion) { this.idDonacion = idDonacion; }

    public List<Bulto> getBultos() { return bultos; }
    public void setBultos(List<Bulto> bultos) { this.bultos = bultos; }

    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }

    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }

    public EstadoEntrega getEstadoDonacion() { return estadoDonacion; }
    public void setEstadoDonacion(EstadoEntrega estadoDonacion) { this.estadoDonacion = estadoDonacion; }
}
