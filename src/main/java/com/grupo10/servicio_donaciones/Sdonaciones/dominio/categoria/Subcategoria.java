package com.grupo10.servicio_donaciones.Sdonaciones.dominio.categoria;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * La Subcategoria es la unidad mínima de asignación dentro del sistema.
 * Permite identificar con precisión qué bien se necesita o se dona.
 * Ej: dentro de "Alimentos" → fideos secos, arroz, legumbres secas.
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subcategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @ManyToOne(fetch = FetchType.EAGER)
    private Categoria categoria;

    public Subcategoria(Categoria categoria, String nombre) {
        setCategoria(categoria);
        this.nombre = nombre;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
        categoria.agregarSubCategoria(this);
    }
}
