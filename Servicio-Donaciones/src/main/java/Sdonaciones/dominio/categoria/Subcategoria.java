package Sdonaciones.dominio.categoria;

/**
 * La Subcategoria es la unidad mínima de asignación dentro del sistema.
 * Permite identificar con precisión qué bien se necesita o se dona.
 * Ej: dentro de "Alimentos" → fideos secos, arroz, legumbres secas.
 */
public class Subcategoria {

    private final String nombre;
    private final int cantidadMinima;
    private final String unidad;
    private final Categoria categoria;

    protected Subcategoria(String nombre, int cantidadMinima, String unidad, Categoria categoria) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre de la subcategoría es obligatorio.");
        if (cantidadMinima < 0)
            throw new IllegalArgumentException("La cantidad mínima no puede ser negativa.");
        if (unidad == null || unidad.isBlank())
            throw new IllegalArgumentException("La unidad de medida es obligatoria.");
        if (categoria == null)
            throw new IllegalArgumentException("La categoría es obligatoria.");

        this.nombre = nombre;
        this.cantidadMinima = cantidadMinima;
        this.unidad = unidad;
        this.categoria = categoria;
    }

    public String getNombre() { return nombre; }
    public int getCantidadMinima() { return cantidadMinima; }
    public String getUnidad() { return unidad; }
    public Categoria getCategoria() { return categoria; }
}
