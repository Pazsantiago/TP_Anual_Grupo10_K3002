package ar.edu.utn.donatrack.dominio.necesidad;

import ar.edu.utn.donatrack.dominio.categoria.Subcategoria;

/**
 * Necesidad declarada por una EntidadBeneficiaria.
 * La subcategoría define QUÉ bien se necesita (unidad mínima).
 */
public abstract class Necesidad {

    private final Subcategoria subcategoria;
    private final String descripcion;
    private final int cantidadObjetivo;
    private int cantidadRecibida;

    protected Necesidad(Subcategoria subcategoria, String descripcion, int cantidadObjetivo) {
        if (subcategoria == null)
            throw new IllegalArgumentException("La subcategoría de la necesidad es obligatoria.");
        if (cantidadObjetivo <= 0)
            throw new IllegalArgumentException("La cantidad objetivo debe ser mayor a cero.");

        this.subcategoria = subcategoria;
        this.descripcion = descripcion;
        this.cantidadObjetivo = cantidadObjetivo;
        this.cantidadRecibida = 0;
    }

    /**
     * Registra la recepción parcial o total de bienes para esta necesidad.
     * @param cantidad cantidad recibida en esta entrega
     */
    public void recibirBienes(int cantidad) {
        if (cantidad <= 0)
            throw new IllegalArgumentException("La cantidad recibida debe ser positiva.");
        this.cantidadRecibida += cantidad;
    }

    /**
     * Indica si la necesidad ya fue satisfecha en el período o acumulado.
     */
    public abstract boolean estaSatisfecha();

    public Subcategoria getSubcategoria() { return subcategoria; }
    public String getDescripcion() { return descripcion; }
    public int getCantidadObjetivo() { return cantidadObjetivo; }
    public int getCantidadRecibida() { return cantidadRecibida; }
}
