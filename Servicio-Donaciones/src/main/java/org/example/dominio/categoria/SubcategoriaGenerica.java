package ar.edu.utn.donatrack.dominio.categoria;

/**
 * Implementación concreta genérica de Subcategoria.
 * Permite crear subcategorías sin necesidad de subclasificar para cada caso.
 * Ejemplos: "Fideos secos", "Arroz", "Camperas de abrigo", "Sillas".
 */
public class SubcategoriaGenerica extends Subcategoria {

    public SubcategoriaGenerica(String nombre, int cantidadMinima,
                                String unidad, Categoria categoria) {
        super(nombre, cantidadMinima, unidad, categoria);
    }
}
