package com.Donatrack.Logistica.domain;

import com.Donatrack.Logistica.domain.EstadoEntrega;
import com.Donatrack.Logistica.domain.Direccion;
import jakarta.persistence.Entity;

public class EntregaDTO {
    private Long id;
    private EstadoEntrega estado;
    private Direccion direccion;

    public EntregaDTO() {}

    public EntregaDTO(Long id, EstadoEntrega estado, Direccion direccion) {
        this.id = id;
        this.estado = estado;
        this.direccion = direccion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public EstadoEntrega getEstado() { return estado; }
    public void setEstado(EstadoEntrega estado) { this.estado = estado; }

    public Direccion getDireccion() { return direccion; }
    public void setDireccion(Direccion direccion) { this.direccion = direccion; }
}
