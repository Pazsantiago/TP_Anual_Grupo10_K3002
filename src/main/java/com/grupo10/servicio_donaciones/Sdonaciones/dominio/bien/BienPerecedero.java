package com.grupo10.servicio_donaciones.Sdonaciones.dominio.bien;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.categoria.Subcategoria;
import jakarta.persistence.Entity;

import java.net.URL;
import java.time.LocalDate;

/**
 * Bien perecedero: extiende Bien con fecha de vencimiento.
 * El sistema puede generar donaciones separadas si difieren en fecha de vencimiento.
 */
@Entity
public class BienPerecedero extends Bien {

    private final LocalDate fechaVencimiento;

    public BienPerecedero(String descripcion, Subcategoria subcategoria,
                          URL foto, UnidadMedida unidad, Integer cantidad, LocalDate fechaVencimiento) {
        super(descripcion, subcategoria, foto, unidad, cantidad);
        if (fechaVencimiento == null) {
            throw new IllegalArgumentException("La fecha de vencimiento es obligatoria para bienes perecederos.");
        }
        if (fechaVencimiento.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de vencimiento no puede ser pasada.");
        }
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean estaVencido() {
        return LocalDate.now().isAfter(fechaVencimiento);
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }
}
