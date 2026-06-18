package Servicio_donaciones.dominio.necesidad;

import Servicio_donaciones.dominio.categoria.Subcategoria;


public class NecesidadExtraordinaria extends Necesidad {

    public NecesidadExtraordinaria(
            String descripcion,
            Subcategoria subcategoria,
            int cantidadObjetivo
    ) {
        super(descripcion, subcategoria, cantidadObjetivo);
    }

    @Override
    public boolean estaSatisfecha() {

        return getCantidadRecibida() >= getCantidadObjetivo();
    }
}