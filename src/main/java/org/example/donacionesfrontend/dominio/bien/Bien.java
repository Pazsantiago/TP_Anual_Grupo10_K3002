package org.example.donacionesfrontend.dominio.bien;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.donacionesfrontend.dominio.categoria.Subcategoria;
import org.example.donacionesfrontend.dominio.donacion.Donacion;

import java.net.URL;

/**
 * Representa un bien material que forma parte de una donación.
 * Cada bien pertenece a una subcategoría (unidad mínima de asignación).
 * Tiene cantidad expresada en la unidad definida por su subcategoría.
 */

@Setter
@Getter
@ToString(exclude = {"donacion"})
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(
                value = BienPerecedero.class,
                name = "PERECEDERO"
        ),
        @JsonSubTypes.Type(
                value = BienConEstado.class,
                name = "CON_ESTADO"
        )
})
public class Bien {
    private Long id;
    private String descripcion;
    private Subcategoria subcategoria;
    private URL foto;
    //    private final Estado estado;
    private UnidadMedida unidad;
    private Integer cantidadOriginal;
    private Integer cantidadActual;
    @JsonIgnore
    private Donacion donacion;

//    public Bien(String descripcion, Subcategoria subcategoria,
//                URL foto, UnidadMedida unidad, Integer cantidad) {

    /// /        if (descripcion == null || descripcion.isBlank())
    /// /            throw new IllegalArgumentException("La descripción del bien es obligatoria.");
    /// /        if (subcategoria == null)
    /// /            throw new IllegalArgumentException("La subcategoría es obligatoria.");
    /// /        if (cantidad <= 0)
    /// /            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
//
//        this.descripcion = descripcion;
//        this.subcategoria = subcategoria;
//        this.unidad = unidad;
//        this.cantidadOriginal = cantidad;
//        this.cantidadActual = cantidad;
//        this.foto = foto;
//    }
    public void setCantidadOriginal(Integer cantidadOriginal) {
        this.cantidadOriginal = cantidadOriginal;
        this.cantidadActual = cantidadOriginal;
    }

    public void restarCantidad(Integer cantidad) {
        this.cantidadActual -= cantidad;
    }

    public void sumarCantidad(Integer cantidad) {
        this.cantidadActual += cantidad;
    }

}
