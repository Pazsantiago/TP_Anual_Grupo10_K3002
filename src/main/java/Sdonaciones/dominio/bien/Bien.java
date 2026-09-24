package Sdonaciones.dominio.bien;

import Sdonaciones.dominio.categoria.Subcategoria;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

/**
 * Representa un bien material que forma parte de una donación.
 * Cada bien pertenece a una subcategoría (unidad mínima de asignación).
 * Tiene cantidad expresada en la unidad definida por su subcategoría.
 */

@Data
@NoArgsConstructor
public class Bien {

    private String descripcion;
    private Subcategoria subcategoria;
    private URL foto;
    //    private final Estado estado;
    private UnidadMedida unidad;
    private Integer cantidadOriginal;
    private Integer cantidadActual;

    public Bien(String descripcion, Subcategoria subcategoria,
                Integer cantidad, Estado estado, URL foto) {
        if (descripcion == null || descripcion.isBlank())
            throw new IllegalArgumentException("La descripción del bien es obligatoria.");
        if (subcategoria == null)
            throw new IllegalArgumentException("La subcategoría es obligatoria.");
        if (cantidad <= 0)
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");

        this.descripcion = descripcion;
        this.subcategoria = subcategoria;
        this.cantidadOriginal = cantidad;
        this.cantidadActual = cantidadOriginal;
//        this.estado = estado;
        this.foto = foto;
    }

    public void restarCantidad(Integer cantidad) {
        this.cantidadActual -= cantidad;
    }

    public void sumarCantidad(Integer cantidad) {
        this.cantidadActual += cantidad;
    }

}
