package Sdonaciones.dominio.bien;

import Sdonaciones.dominio.categoria.Subcategoria;

import java.net.URL;
import java.time.LocalDate;

/**
 * Bien perecedero: extiende Bien con fecha de vencimiento.
 * El sistema puede generar donaciones separadas si difieren en fecha de vencimiento.
 */
public class BienPerecedero extends Bien {

    private final LocalDate fechaVencimiento;

    public BienPerecedero(String descripcion, Subcategoria subcategoria,
                          int cantidad, LocalDate fechaVencimiento, URL foto) {
        super(descripcion, subcategoria, cantidad, null, foto);
        if (fechaVencimiento == null)
            throw new IllegalArgumentException("La fecha de vencimiento es obligatoria para bienes perecederos.");
        if (fechaVencimiento.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("La fecha de vencimiento no puede ser pasada.");
        this.fechaVencimiento = fechaVencimiento;
    }

    public BienPerecedero(String descripcion, Subcategoria subcategoria,
                          int cantidad, LocalDate fechaVencimiento) {
        this(descripcion, subcategoria, cantidad, fechaVencimiento, null);
    }

    public boolean estaVencido() {
        return LocalDate.now().isAfter(fechaVencimiento);
    }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
}
