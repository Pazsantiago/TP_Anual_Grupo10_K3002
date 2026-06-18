package Servicio_donaciones.dominio.bien;

import Servicio_donaciones.dominio.categoria.*;

import java.net.URL;

/**
 * Representa un bien material que forma parte de una donación.
 * Cada bien pertenece a una subcategoría (unidad mínima de asignación).
 * Tiene cantidad expresada en la unidad definida por su subcategoría.
 */
public class Bien {

    private final String descripcion;
    private final Subcategoria subcategoria;
    private final URL foto;
    private final int cantidad;

    public Bien(String descripcion, Subcategoria subcategoria,
                int cantidad, Estado estado, URL foto) {
        if (descripcion == null || descripcion.isBlank())
            throw new IllegalArgumentException("La descripción del bien es obligatoria.");
        if (subcategoria == null)
            throw new IllegalArgumentException("La subcategoría es obligatoria.");
        if (cantidad <= 0)
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");

        this.descripcion = descripcion;
        this.subcategoria = subcategoria;
        this.cantidad = cantidad;
        this.foto = foto;
    }

    /** Constructor sin foto ni estado (bienes sin distinción de uso). */
    public Bien(String descripcion, Subcategoria subcategoria, int cantidad) {
        this(descripcion, subcategoria, cantidad, null, null);
    }


    public String getDescripcion() { return descripcion; }
    public Subcategoria getSubcategoria() { return subcategoria; }
    public URL getFoto() { return foto; }

    public int getCantidad() { return cantidad; }
    @Override
    public String toString(){
        return subcategoria.getNombre() + " x "+cantidad+ subcategoria.getUnidad();
    }
}
