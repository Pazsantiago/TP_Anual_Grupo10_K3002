package Sdonaciones.dominio.necesidad;

import Sdonaciones.dominio.categoria.Subcategoria;

import java.time.LocalDate;


public class NecesidadExtraordinaria extends Necesidad {

    public NecesidadExtraordinaria(
            String descripcion,
            Subcategoria subcategoria,
            int cantidadObjetivo
    ) {
        super(descripcion, subcategoria, cantidadObjetivo);
    }

    public LocalDate getSatisfechaEn(){
        return LocalDate.now();
    }

    @Override
    public boolean estaSatisfecha() {

        return getCantidadRecibida() >= getCantidadObjetivo();
    }
}