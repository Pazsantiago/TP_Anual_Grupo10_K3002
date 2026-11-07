package com.Donatrack.Logistica.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "entregas")
public class Entrega {

    @Id private String id;

    @Indexed(unique = true, sparse = true)
    private Long idDonacion;                       // idAsignada de Donaciones
    @Indexed private List<Long> idDonantes = new ArrayList<>();
    @Indexed private Long idEntidad;
    @Indexed private String planRutaId;
    @Indexed private EstadoEntrega estado = EstadoEntrega.PENDIENTE;

    private List<Long> idSegmentadas = new ArrayList<>();
    private Integer nroComprobante;
    private String patenteCamion;
    private Direccion direccion;
    private List<Bulto> bultos = new ArrayList<>();
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private LocalDateTime fechaEntrega;
    private List<String> fotos = new ArrayList<>();
    private String motivoFallo;

    public Entrega() {}
    public Entrega(Direccion direccion) { this.direccion = direccion; }

    // PENDIENTE -> EN_TRASLADO -> ENTREGADA | NO_RECIBIDA -> PENDIENTE

    public void asignarAPlan(String planRutaId, String patente) {
        if (estado != EstadoEntrega.PENDIENTE)
            throw new IllegalStateException("Solo se asigna una entrega PENDIENTE, estado: " + estado);
        this.planRutaId = planRutaId;
        this.patenteCamion = patente;
    }

    public void iniciarTraslado() {
        if (estado != EstadoEntrega.PENDIENTE || planRutaId == null)
            throw new IllegalStateException("La entrega debe estar PENDIENTE y asignada a una ruta");
        this.estado = EstadoEntrega.EN_TRASLADO;
    }

    public void marcarEntregada() {
        if (estado != EstadoEntrega.EN_TRASLADO)
            throw new IllegalStateException("Solo se entrega una entrega EN_TRASLADO, estado: " + estado);
        this.estado = EstadoEntrega.ENTREGADA;
        this.fechaEntrega = LocalDateTime.now();
        this.nroComprobante = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }

    public void marcarNoRecibida(String motivo) {
        if (motivo == null || motivo.isBlank())
            throw new IllegalArgumentException("El motivo es obligatorio");
        if (estado != EstadoEntrega.EN_TRASLADO)
            throw new IllegalStateException("Solo se informa no recibida una entrega EN_TRASLADO, estado: " + estado);
        this.estado = EstadoEntrega.NO_RECIBIDA;
        this.motivoFallo = motivo;
    }

    public void volverAPendiente() {
        if (estado != EstadoEntrega.NO_RECIBIDA)
            throw new IllegalStateException("Solo vuelve a PENDIENTE una entrega NO_RECIBIDA, estado: " + estado);
        this.estado = EstadoEntrega.PENDIENTE;
        this.planRutaId = null;
        this.patenteCamion = null;
        this.motivoFallo = null;
    }

    public void agregarFotos(List<String> nuevas) {
        if (estado != EstadoEntrega.ENTREGADA)
            throw new IllegalStateException("Las fotos se cargan sobre una entrega ENTREGADA");
        if (nuevas != null) fotos.addAll(nuevas);
    }

    public double pesoTotal()    { return bultos.stream().mapToDouble(Bulto::getPeso).sum(); }
    public double volumenTotal() { return bultos.stream().mapToDouble(Bulto::getVolumen).sum(); }
    public double alturaMaxima() { return bultos.stream().mapToDouble(Bulto::getAltura).max().orElse(0); }

    public String getId() { return id; }
    public Long getIdDonacion() { return idDonacion; }
    public void setIdDonacion(Long v) { this.idDonacion = v; }
    public List<Long> getIdDonantes() { return idDonantes; }
    public void setIdDonantes(List<Long> v) { this.idDonantes = v == null ? new ArrayList<>() : new ArrayList<>(v); }
    public Long getIdEntidad() { return idEntidad; }
    public void setIdEntidad(Long v) { this.idEntidad = v; }
    public List<Long> getIdSegmentadas() { return idSegmentadas; }
    public void setIdSegmentadas(List<Long> v) { this.idSegmentadas = v == null ? new ArrayList<>() : new ArrayList<>(v); }
    public Integer getNroComprobante() { return nroComprobante; }
    public String getPlanRutaId() { return planRutaId; }
    public EstadoEntrega getEstado() { return estado; }
    public String getPatenteCamion() { return patenteCamion; }
    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion d) { this.direccion = d; }
    public List<Bulto> getBultos() { return bultos; }
    public void setBultos(List<Bulto> b) { this.bultos = b == null ? new ArrayList<>() : new ArrayList<>(b); }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public List<String> getFotos() { return fotos; }
    public String getMotivoFallo() { return motivoFallo; }
}