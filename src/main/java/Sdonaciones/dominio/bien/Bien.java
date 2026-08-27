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
    private Integer cantidad;

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
        this.cantidad = cantidad;
//        this.estado = estado;
        this.foto = foto;
    }

    /**
     * Constructor sin foto ni estado (bienes sin distinción de uso).
     */
//    public Bien(String descripcion, Subcategoria subcategoria, Integer cantidad) {
//        this(descripcion, subcategoria, cantidad, null, null);
//    }

//    public boolean requiereEstado() {
//        return estado != null;
//    }

//    @Override
//    public String toString(){
//        return subcategoria.getNombre() + " x "+cantidad+ subcategoria.getUnidad();
//    }
}
