package Sdonaciones.dominio.necesidad;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NecesidadExtraordinaria extends Necesidad {

//    public NecesidadExtraordinaria(
//            String descripcion,
//            Subcategoria subcategoria,
//            Integer cantidadObjetivo
//    ) {
//        super(descripcion, subcategoria, cantidadObjetivo);
//    }

    public LocalDate getSatisfechaEn() {
        return LocalDate.now();
    }

    @Override
    public boolean estaSatisfecha() {

        return getCantidadRecibida() >= getCantidadObjetivo();
    }
}