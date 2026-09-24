package Sdonaciones.dominio.necesidad;

import Sdonaciones.dominio.categoria.Subcategoria;
import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NecesidadRecurrente extends Necesidad {
    private Periodo periodo;
    private Integer cantidadRecibidaEnPeriodo;

    public NecesidadRecurrente(
            String descripcion,
            Subcategoria subcategoria,
            Integer cantidadObjetivo,
            Periodo periodo,
            EntidadBeneficiaria entidad
    ) {
        super(descripcion, subcategoria, cantidadObjetivo, entidad);

        this.periodo = periodo;
        this.cantidadRecibidaEnPeriodo = 0;
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
                && cantidadRecibidaEnPeriodo >= getCantidadObjetivo();
    }


}