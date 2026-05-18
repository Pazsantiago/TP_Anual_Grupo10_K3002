package ar.edu.utn.donatrack.dominio.donacion;

import ar.edu.utn.donatrack.dominio.bien.Bien;
import ar.edu.utn.donatrack.dominio.categoria.Subcategoria;

import java.util.UUID;

/**
 * Una DonacionSegmentada es el resultado de dividir una Donacion por subcategoría.
 * Es la unidad mínima de asignación: cada una queda asociada a una única Subcategoria.
 * El sistema la genera automáticamente al registrar la donación.
 */
public class DonacionSegmentada {

    private final String id;
    private final Bien bien;
    private final Subcategoria subcategoria;
    private EstadoDonacion estado;

    public DonacionSegmentada(Bien bien) {
        if (bien == null)
            throw new IllegalArgumentException("El bien es obligatorio en una donación segmentada.");
        this.id = UUID.randomUUID().toString();
        this.bien = bien;
        this.subcategoria = bien.getSubcategoria();
        this.estado = EstadoDonacion.PENDIENTE_ASIGNACION;
    }

    public void marcarAsignada() {
        if (this.estado != EstadoDonacion.PENDIENTE_ASIGNACION)
            throw new IllegalStateException("Solo se puede asignar una donación pendiente.");
        this.estado = EstadoDonacion.ASIGNADA;
    }

    public void marcarEnTransito() {
        if (this.estado != EstadoDonacion.ASIGNADA)
            throw new IllegalStateException("Solo se puede poner en tránsito una donación asignada.");
        this.estado = EstadoDonacion.EN_TRANSITO;
    }

    public void marcarEntregada() {
        if (this.estado != EstadoDonacion.EN_TRANSITO)
            throw new IllegalStateException("Solo se puede entregar una donación en tránsito.");
        this.estado = EstadoDonacion.ENTREGADA;
    }

    public void marcarVencida() {
        this.estado = EstadoDonacion.VENCIDA;
    }

    public String getId() { return id; }
    public Bien getBien() { return bien; }
    public Subcategoria getSubcategoria() { return subcategoria; }
    public EstadoDonacion getEstado() { return estado; }
}
