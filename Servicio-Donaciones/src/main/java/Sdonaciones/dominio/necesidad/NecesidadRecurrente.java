package ar.edu.utn.donatrack.dominio.necesidad;

import ar.edu.utn.donatrack.dominio.categoria.Subcategoria;

import java.time.YearMonth;

/**
 * Necesidad recurrente: consumo habitual de la organización en un período.
 * Se satisface dentro del período (semanal, mensual) con la cantidad objetivo.
 * Ejemplo: comedor necesita 100 paquetes de fideos por semana.
 */
public class NecesidadRecurrente extends Necesidad {

    public enum Periodo { SEMANAL, QUINCENAL, MENSUAL }

    private final Periodo periodo;
    private YearMonth periodoActual;
    private int cantidadRecibidaEnPeriodo;

    public NecesidadRecurrente(Subcategoria subcategoria, String descripcion,
                               int cantidadObjetivo, Periodo periodo) {
        super(subcategoria, descripcion, cantidadObjetivo);
        if (periodo == null)
            throw new IllegalArgumentException("El período es obligatorio para necesidades recurrentes.");
        this.periodo = periodo;
        this.periodoActual = YearMonth.now();
        this.cantidadRecibidaEnPeriodo = 0;
    }

    /** Reinicia el contador si cambió el período. */
    public void actualizarPeriodo() {
        YearMonth ahora = YearMonth.now();
        if (!ahora.equals(periodoActual)) {
            periodoActual = ahora;
            cantidadRecibidaEnPeriodo = 0;
        }
    }

    @Override
    public void recibirBienes(int cantidad) {
        actualizarPeriodo();
        super.recibirBienes(cantidad);
        this.cantidadRecibidaEnPeriodo += cantidad;
    }

    @Override
    public boolean estaSatisfecha() {
        actualizarPeriodo();
        return cantidadRecibidaEnPeriodo >= getCantidadObjetivo();
    }

    public Periodo getPeriodo() { return periodo; }
    public int getCantidadRecibidaEnPeriodo() { return cantidadRecibidaEnPeriodo; }
}
