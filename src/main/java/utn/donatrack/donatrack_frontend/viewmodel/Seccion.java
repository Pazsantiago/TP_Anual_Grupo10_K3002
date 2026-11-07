package utn.donatrack.donatrack_frontend.viewmodel;

import java.util.List;
import java.util.function.Supplier;
import utn.donatrack.donatrack_frontend.client.ServicioExternoException;

/**
 * Una parte de una pagina que se carga desde un servicio. Si el servicio falla, la pagina igual se muestra:
 * esa seccion trae la lista vacia y el mensaje de error, y las demas siguen funcionando.
 *
 * Uso en un controller:  model.addAttribute("donantes", Seccion.cargar(client::listarDonantes));
 * Uso en la vista:       ${donantes.datos} y ${donantes.error}
 */
public record Seccion<T>(List<T> datos, String error) {

    public static <T> Seccion<T> cargar(Supplier<List<T>> origen) {
        try {
            List<T> datos = origen.get();
            return new Seccion<>(datos == null ? List.of() : datos, null);
        } catch (ServicioExternoException e) {
            return new Seccion<>(List.of(), e.getMessage());
        }
    }

    public static <T> Seccion<T> vacia() {
        return new Seccion<>(List.of(), null);
    }
}
