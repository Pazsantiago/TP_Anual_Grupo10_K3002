package ar.edu.utn.donatrack.dominio.categoria;

/**
 * Factory para instanciar categorías concretas del sistema.
 * Nuevas categorías se agregan aquí sin modificar el resto del dominio.
 */
public class CategoriaFactory {

    public enum TipoCategoria {
        ALIMENTOS, VESTIMENTA, MOBILIARIO
    }

    public static Categoria crear(TipoCategoria tipo) {
        return switch (tipo) {
            case ALIMENTOS  -> new CategoriaAlimentos();
            case VESTIMENTA -> new CategoriaVestimenta();
            case MOBILIARIO -> new CategoriaMobiliario();
        };
    }

    // También permite crear una categoría genérica por nombre (extensión futura)
    public static Categoria crearGenerica(String nombre) {
        return new Categoria(nombre) {};
    }
}
