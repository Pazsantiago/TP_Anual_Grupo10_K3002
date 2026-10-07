package com.grupo10.servicio_donaciones.Sdonaciones.dominio.bien;


import com.grupo10.servicio_donaciones.Sdonaciones.dominio.categoria.Subcategoria;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.net.URL;

@Entity
public class BienConEstado extends Bien {
    @Enumerated(EnumType.STRING)
    private Estado estado;

    public BienConEstado(String descripcion, Subcategoria subcategoria, URL foto, UnidadMedida unidad, Integer cantidad, Estado estado) {
        super(descripcion, subcategoria, foto, unidad, cantidad);
        this.estado = estado;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
