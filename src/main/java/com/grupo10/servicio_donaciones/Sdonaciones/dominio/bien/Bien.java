package com.grupo10.servicio_donaciones.Sdonaciones.dominio.bien;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.categoria.Subcategoria;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.Donacion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Bien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "id_subcategoria")
    private Subcategoria subcategoria;
    private URL foto;
    //    private final Estado estado;
    @Enumerated(EnumType.STRING)
    private UnidadMedida unidad;
    private Integer cantidadOriginal;
    private Integer cantidadActual;
    @JsonIgnore
    @ManyToOne()
    private Donacion donacion;

    public Bien(String descripcion, Subcategoria subcategoria,
                URL foto, UnidadMedida unidad, Integer cantidad) {
        if (descripcion == null || descripcion.isBlank())
            throw new IllegalArgumentException("La descripción del bien es obligatoria.");
        if (subcategoria == null)
            throw new IllegalArgumentException("La subcategoría es obligatoria.");
        if (cantidad <= 0)
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");

        this.descripcion = descripcion;
        this.subcategoria = subcategoria;
        this.unidad = unidad;
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
