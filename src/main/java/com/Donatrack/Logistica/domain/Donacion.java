package com.Donatrack.Logistica.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

/*import jakarta.persistence.Entity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import java.util.List; */

@Document(collection = "donaciones")
public class Donacion {
    @Id
    private String idDonacion;

    private List<Bulto> bultos = new ArrayList<>();
    private Direccion direccion;
    private String destinatario;
    private EstadoEntrega estadoDonacion;

    public Donacion() {}

    public Donacion(String idDonacion, List<Bulto> bultos, Direccion direccion,
                    String destinatario, EstadoEntrega estadoDonacion) {
        this.idDonacion = idDonacion;
        this.bultos = bultos;
        this.direccion = direccion;
        this.destinatario = destinatario;
        this.estadoDonacion = estadoDonacion;
    }

    public String getIdDonacion() { return idDonacion; }
    public void setIdDonacion(String idDonacion) { this.idDonacion = idDonacion; }
    public List<Bulto> getBultos() { return bultos; }
    public void setBultos(List<Bulto> bultos) { this.bultos = bultos; }
    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }
    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public EstadoEntrega getEstadoDonacion() { return estadoDonacion; }
    public void setEstadoDonacion(EstadoEntrega e) { this.estadoDonacion = e; }
}