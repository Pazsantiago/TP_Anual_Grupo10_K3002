package com.Donatrack.Logistica.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** Una solicitud al proveedor externo (máximo 100 donaciones). */
@Document(collection = "lotesPlanificacion")
public class LoteRuta {
    public enum Estado { ENVIADO, RESPONDIDO, ERROR }

    @Id private String id;
    private LocalDate fecha;
    private List<String> entregaIds = new ArrayList<>();
    private Estado estado = Estado.ENVIADO;
    private LocalDateTime fechaEnvio = LocalDateTime.now();
    private LocalDateTime fechaRespuesta;
    private String error;

    public LoteRuta() {}
    public LoteRuta(LocalDate fecha, List<String> entregaIds) {
        this.fecha = fecha;
        this.entregaIds = new ArrayList<>(entregaIds);
    }

    public void marcarRespondido() { this.estado = Estado.RESPONDIDO; this.fechaRespuesta = LocalDateTime.now(); }
    public void marcarError(String e) { this.estado = Estado.ERROR; this.error = e; }

    public String getId() { return id; }
    public LocalDate getFecha() { return fecha; }
    public List<String> getEntregaIds() { return entregaIds; }
    public Estado getEstado() { return estado; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public LocalDateTime getFechaRespuesta() { return fechaRespuesta; }
    public String getError() { return error; }
}