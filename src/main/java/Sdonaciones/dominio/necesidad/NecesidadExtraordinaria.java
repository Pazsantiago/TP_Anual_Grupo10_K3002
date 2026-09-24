package Sdonaciones.dominio.necesidad;

import Sdonaciones.dominio.categoria.Subcategoria;
import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NecesidadExtraordinaria extends Necesidad {

    public NecesidadExtraordinaria(
            String descripcion,
            Subcategoria subcategoria,
            Integer cantidadObjetivo,
            Integer cantidadRecibida,
            EntidadBeneficiaria entidad
    ) {
        super(descripcion, subcategoria, cantidadObjetivo, entidad);
    }

    @Override
    public void aplicarActualizacion(Necesidad necesidad) {
    }


    public LocalDate getSatisfechaEn() {
        return LocalDate.now();
    }

    @Override
    public boolean estaSatisfecha() {

        return getCantidadRecibida() >= getCantidadObjetivo();
    }
}