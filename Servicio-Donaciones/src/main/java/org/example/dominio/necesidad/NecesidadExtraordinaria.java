package ar.edu.utn.donatrack.dominio.necesidad;

import ar.edu.utn.donatrack.dominio.categoria.Subcategoria;

/**
 * Necesidad extraordinaria: situación excepcional (inundación, incendio, mudanza).
 * Se satisface cuando la cantidad recibida >= cantidad objetivo.
 * Admite satisfacción parcial: múltiples donaciones pueden cubrir la necesidad.
 * Ejemplo: escuela rural necesita 30 sillas tras una inundación.
 */
public class NecesidadExtraordinaria extends Necesidad {

    public NecesidadExtraordinaria(Subcategoria subcategoria,
                                   String descripcion,
                                   int cantidadObjetivo) {
        super(subcategoria, descripcion, cantidadObjetivo);
    }

    @Override
    public boolean estaSatisfecha() {
        return getCantidadRecibida() >= getCantidadObjetivo();
    }
}
