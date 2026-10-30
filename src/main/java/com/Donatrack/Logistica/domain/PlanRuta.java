package com.Donatrack.Logistica.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ElementCollection;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "planesDeRutas")
@CompoundIndex(name = "fecha_camion", def = "{'fecha': 1, 'patenteCamion': 1}")

public class PlanRuta {
    @Id
    private String id;
 private LocalDate fecha;                 // jornada operativa
    private String idCamion;
    private String patenteCamion;            // snapshot
    private EstadoPlan estado = EstadoPlan.PLANIFICADA;

    private List<Parada> paradas = new ArrayList<>();

    private Double distanciaTotal;
    private String tiempoEstimado;

    private String idLote;                   // lote enviado al proveedor
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    public PlanRuta() {}

    public PlanRuta(LocalDate fecha, String idCamion, String patenteCamion,
                    List<Parada> paradas, Double distanciaTotal, String tiempoEstimado) {
        this.fecha = fecha;
        this.idCamion = idCamion;
        this.patenteCamion = patenteCamion;
        this.paradas = paradas;
        this.distanciaTotal = distanciaTotal;
        this.tiempoEstimado = tiempoEstimado;
    }

    // ---- lógica de negocio ----

    public void iniciar() {
        if (estado != EstadoPlan.PLANIFICADA) {
            throw new IllegalStateException("Solo se puede iniciar una ruta PLANIFICADA, estado actual: " + estado);
        }
        this.estado = EstadoPlan.EN_CURSO;
        this.fechaInicio = LocalDateTime.now();
    }

    public void finalizar() {
        if (estado != EstadoPlan.EN_CURSO) {
            throw new IllegalStateException("Solo se puede finalizar una ruta EN_CURSO, estado actual: " + estado);
        }
        this.estado = EstadoPlan.FINALIZADA;
        this.fechaFin = LocalDateTime.now();
    }

    public List<String> entregaIds() {
        return paradas.stream().flatMap(p -> p.getEntregaIds().stream()).toList();
    }

    // ---- getters / setters ----

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getIdCamion() { return idCamion; }
    public void setIdCamion(String idCamion) { this.idCamion = idCamion; }
    public String getPatenteCamion() { return patenteCamion; }
    public void setPatenteCamion(String patenteCamion) { this.patenteCamion = patenteCamion; }
    public EstadoPlan getEstado() { return estado; }
    public void setEstado(EstadoPlan estado) { this.estado = estado; }
    public List<Parada> getParadas() { return paradas; }
    public void setParadas(List<Parada> paradas) { this.paradas = paradas; }
    public Double getDistanciaTotal() { return distanciaTotal; }
    public void setDistanciaTotal(Double d) { this.distanciaTotal = d; }
    public String getTiempoEstimado() { return tiempoEstimado; }
    public void setTiempoEstimado(String t) { this.tiempoEstimado = t; }
    public String getIdLote() { return idLote; }
    public void setIdLote(String idLote) { this.idLote = idLote; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public LocalDateTime getFechaFin() { return fechaFin; }
}