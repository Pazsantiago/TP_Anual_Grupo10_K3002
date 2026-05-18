package ar.edu.utn.donatrack.dominio.categoria;

import java.util.ArrayList;
import java.util.List;

public abstract class Categoria {

    private final String nombre;
    private final List<Subcategoria> subcategorias;

    protected Categoria(String nombre) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio.");
        this.nombre = nombre;
        this.subcategorias = new ArrayList<>();
    }

    public void agregarSubcategoria(Subcategoria subcategoria) {
        if (subcategoria == null)
            throw new IllegalArgumentException("La subcategoría no puede ser nula.");
        this.subcategorias.add(subcategoria);
    }

    public String getNombre() { return nombre; }

    public List<Subcategoria> getSubcategorias() { return List.copyOf(subcategorias); }
}
