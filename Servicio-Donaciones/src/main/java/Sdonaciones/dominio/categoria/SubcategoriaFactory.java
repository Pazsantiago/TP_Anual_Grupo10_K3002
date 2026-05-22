package Sdonaciones.dominio.categoria;

import java.util.*;

public class SubcategoriaFactory {

    private static final Map<String, Subcategoria> instancias = new HashMap<>();

    static {
        registrar(new Subcategoria("Silla",          1, "unidades", Categoria.MOBILIARIO));
        registrar(new Subcategoria("Mesa",            1, "unidades", Categoria.MOBILIARIO));
        registrar(new Subcategoria("Fideos secos",    1, "paquetes", Categoria.ALIMENTICIO));
        registrar(new Subcategoria("Arroz",           1, "kg",       Categoria.ALIMENTICIO));
        registrar(new Subcategoria("Campera abrigo",  1, "unidades", Categoria.VESTIMENTA));
        // ...
    }

    public static void registrar(Subcategoria subcategoria) {
        if (instancias.containsKey(subcategoria.getNombre().toLowerCase())) {
            throw new IllegalStateException("La subcategoria ya existe: " + subcategoria.getNombre());
        }
        instancias.put(subcategoria.getNombre().toLowerCase(), subcategoria);
    }

    public static Subcategoria obtener(String nombre) {
        Subcategoria subcategoria = instancias.get(nombre.toLowerCase());
        if (subcategoria == null) {
            throw new IllegalArgumentException("Subcategoria no encontrada: " + nombre);
        }
        return subcategoria;
    }

    public static List<Subcategoria> listarTodas() {
        return List.copyOf(instancias.values());
    }
}