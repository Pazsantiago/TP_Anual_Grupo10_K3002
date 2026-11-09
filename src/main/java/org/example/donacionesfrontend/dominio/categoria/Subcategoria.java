package org.example.donacionesfrontend.dominio.categoria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * La Subcategoria es la unidad mínima de asignación dentro del sistema.
 * Permite identificar con precisión qué bien se necesita o se dona.
 * Ej: dentro de "Alimentos" → fideos secos, arroz, legumbres secas.
 */
@Getter
@Setter
@NoArgsConstructor
public class Subcategoria {
    private Long id;
    private String nombre;
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
