package Sdonaciones.dominio.categoria;

import java.util.ArrayList;
import java.util.List;
public class Categoria {

    public static final Categoria MOBILIARIO = new Categoria(TipoCategoria.MOBILIARIO);
    public static final Categoria ALIMENTICIO = new Categoria(TipoCategoria.ALIMENTICIO);
    public static final Categoria PERECEDERO = new Categoria(TipoCategoria.PERECEDERO);
    public static final Categoria VESTIMENTA = new Categoria(TipoCategoria.VESTIMENTA);

    private String nombre;
    private final List<Subcategoria> subcategorias = new ArrayList<>();
    private final TipoCategoria tipo;

    private Categoria(TipoCategoria tipo) {
        this.tipo = tipo;
    }

    public void addSubcategoria(Subcategoria sub) {
        subcategorias.add(sub);
    }

    public List<Subcategoria> getSubcategorias() {
        return List.copyOf(subcategorias);
    }

    public TipoCategoria getTipo() {
        return tipo;
    }
}

