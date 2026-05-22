package Sdonaciones.dominio.necesidad;

import Sdonaciones.dominio.categoria.Subcategoria;

import java.time.LocalDate;

public class NecesidadRecurrente extends Necesidad {
    private Periodo periodo;
    private LocalDate fechaInicioPeriodo;
    private int cantidadRecibidaEnPeriodo;

    public NecesidadRecurrente(
            String descripcion,
            Subcategoria subcategoria,
            int cantidadObjetivo,
            Periodo periodo,
            LocalDate fechaInicioPeriodo
    ) {
        super(descripcion, subcategoria, cantidadObjetivo);

        this.periodo = periodo;
        this.fechaInicioPeriodo = fechaInicioPeriodo;
        this.cantidadRecibidaEnPeriodo = 0;
    }

    @Override
    public void recibirBienes(int cantidad) {

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
                fechaInicioPeriodo.plusDays(periodo.getValue());

        return !LocalDate.now().isAfter(finPeriodo);
    }

    private void reiniciarPeriodo() {

        this.cantidadRecibidaEnPeriodo = 0;
        this.fechaInicioPeriodo =
                this.fechaInicioPeriodo.plusDays(periodo.getValue());
    }

    @Override
    public boolean estaSatisfecha() {

        return periodoVigente()
                && cantidadRecibidaEnPeriodo >= getCantidadObjetivo();
    }

    public Periodo getPeriodo() {return periodo;}
    public void setPeriodo(Periodo periodo) {this.periodo = periodo;}

    public LocalDate getFechaInicioPeriodo() {
        return fechaInicioPeriodo;
    }

    public void setFechaInicioPeriodo(LocalDate fechaInicioPeriodo) {
        this.fechaInicioPeriodo = fechaInicioPeriodo;
    }

    public int getCantidadRecibidaEnPeriodo() {
        return cantidadRecibidaEnPeriodo;
    }

    public void setCantidadRecibidaEnPeriodo(int cantidadRecibidaEnPeriodo) {
        this.cantidadRecibidaEnPeriodo = cantidadRecibidaEnPeriodo;
    }
}