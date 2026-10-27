package com.grupo10.servicio_donaciones.Sdonaciones.dominio.bien;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Bien perecedero: extiende Bien con fecha de vencimiento.
 * El sistema puede generar donaciones separadas si difieren en fecha de vencimiento.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class BienPerecedero extends Bien {
    private LocalDate fechaVencimiento;

//    public BienPerecedero(String descripcion, Subcategoria subcategoria,
//                          URL foto, UnidadMedida unidad, Integer cantidad, LocalDate fechaVencimiento) {
//        super(descripcion, subcategoria, foto, unidad, cantidad);
//        if (fechaVencimiento == null) {
//            throw new IllegalArgumentException("La fecha de vencimiento es obligatoria para bienes perecederos.");
//        }
//        if (fechaVencimiento.isBefore(LocalDate.now())) {
//            throw new IllegalArgumentException("La fecha de vencimiento no puede ser pasada.");
//        }
//        this.fechaVencimiento = fechaVencimiento;
//    }

    public Boolean estaVencido(Integer diasTolerablesDeVencimiento) {
        return fechaVencimiento.isBefore(LocalDate.now().plusDays(diasTolerablesDeVencimiento));
    }

}
