package com.grupo10.servicio_donaciones.Sdonaciones.dominio.necesidad;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.categoria.Subcategoria;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class NecesidadRecurrente extends Necesidad {
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Periodo periodo;
    private Integer cantidadRecibidaEnPeriodo = 0;


    public NecesidadRecurrente(
            String descripcion,
            Subcategoria subcategoria,
            Integer cantidadObjetivo,
            Integer cantidadRecibida,
            Periodo periodo,
            EntidadBeneficiaria entidad,
            Boolean estaSatisfecha
    ) {
        super(descripcion, subcategoria, cantidadObjetivo, cantidadRecibida, entidad, estaSatisfecha);
        this.periodo = periodo;
        this.setCantidadRecibidaEnPeriodo(cantidadRecibidaEnPeriodo);
    }

    public void setCantidadRecibidaEnPeriodo(Integer cantidadRecibidaEnPeriodo) {
        this.cantidadRecibidaEnPeriodo = cantidadRecibidaEnPeriodo == null ? 0 : cantidadRecibidaEnPeriodo;
    }

    @Override
    public void aplicarActualizacion(Necesidad necesidad) {
        NecesidadRecurrente recurrente = (NecesidadRecurrente) necesidad;
        this.setCantidadRecibida(recurrente.getCantidadRecibida());
        this.setDescripcion(recurrente.getDescripcion());
        this.setEntidadBeneficiaria(recurrente.getEntidadBeneficiaria());
        this.setSubcategoria(recurrente.getSubcategoria());
        this.setCantidadObjetivo(recurrente.getCantidadObjetivo());
        this.setPeriodo(recurrente.getPeriodo());
        this.setCantidadRecibidaEnPeriodo(recurrente.getCantidadRecibidaEnPeriodo());
        this.setCantidadRecibida(recurrente.getCantidadRecibida());
    }

    @Override
    public void recibirBienes(Integer cantidad) {

        // si el período ya venció, reinicia el conteo
        if (!periodoVigente()) {
            reiniciarPeriodo();
        }

        this.cantidadRecibidaEnPeriodo += cantidad;

        // mantiene también el acumulado general
        super.recibirBienes(cantidad);
    }

    public boolean periodoVigente() {

        LocalDate finPeriodo =
                periodo.getInicioPeriodo().plusDays(periodo.getPeriodoDias());

        return !LocalDate.now().isAfter(finPeriodo);
    }

    private void reiniciarPeriodo() {

        this.cantidadRecibidaEnPeriodo = 0;
        this.periodo.setInicioPeriodo(this.periodo.getInicioPeriodo().plusDays(periodo.getPeriodoDias()));
    }

    public LocalDate getSatisfechaEn() {
        return LocalDate.now();
    }

    @Override
    public boolean estaSatisfecha() {

        return periodoVigente()
                && cantidadRecibidaEnPeriodo >= getCantidadObjetivo() && this.estaSatisfecha();
    }


}