package com.Donatrack.Logistica.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Entrega {
    public enum Estado { PENDIENTE, EN_TRASLADO, ENTREGADA, NO_RECIBIDA }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.PENDIENTE;

    @Embedded
    private Direccion direccion;
    private LocalDateTime fechaEntrega;

    public Entrega() {
    }

    public Entrega(Direccion direccion) {
        this.direccion = direccion;
    }

    public Long getId() { return id; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }
    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }
}
