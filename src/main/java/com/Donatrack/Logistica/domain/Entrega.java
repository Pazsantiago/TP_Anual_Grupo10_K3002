package com.Donatrack.Logistica.domain;

/*import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime; */

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "entregas")
public class Entrega {

    @Id
    private String id;

    @Indexed private String idDonacion;
    @Indexed private String idDonante;     // para el mapa del donante (req. 9)
    @Indexed private String idEntidad;     // para el mapa de la entidad (req. 14)
    @Indexed private String planRutaId;

    private String patenteCamion;          // camión que realizó la entrega
    private EstadoEntrega estado = EstadoEntrega.PENDIENTE;
    private Direccion direccion;
    private LocalDateTime fechaEntrega;
    private List<String> fotos = new ArrayList<>();
    private String motivoFallo;

    public Entrega() {}

    public Entrega(Direccion direccion) { this.direccion = direccion; }

    public void marcarEntregada(String patente) {
        this.estado = EstadoEntrega.ENTREGADA;
        this.patenteCamion = patente;
        this.fechaEntrega = LocalDateTime.now();
    }

    public void marcarNoRecibida(String motivo) {
        this.estado = EstadoEntrega.NO_RECIBIDA;
        this.motivoFallo = motivo;
    }

    public void volverAPendiente() {
        this.estado = EstadoEntrega.PENDIENTE;
        this.planRutaId = null;
        this.patenteCamion = null;
    }

    public String getId() { return id; }
    public String getIdDonacion() { return idDonacion; }
    public void setIdDonacion(String v) { this.idDonacion = v; }
    public String getIdDonante() { return idDonante; }
    public void setIdDonante(String v) { this.idDonante = v; }
    public String getIdEntidad() { return idEntidad; }
    public void setIdEntidad(String v) { this.idEntidad = v; }
    public String getPlanRutaId() { return planRutaId; }
    public void setPlanRutaId(String v) { this.planRutaId = v; }
    public String getPatenteCamion() { return patenteCamion; }
    public void setPatenteCamion(String v) { this.patenteCamion = v; }
    public EstadoEntrega getEstado() { return estado; }
    public void setEstado(EstadoEntrega estado) { this.estado = estado; }
    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion d) { this.direccion = d; }
    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime f) { this.fechaEntrega = f; }
    public List<String> getFotos() { return fotos; }
    public void setFotos(List<String> fotos) { this.fotos = fotos; }
    public String getMotivoFallo() { return motivoFallo; }
}