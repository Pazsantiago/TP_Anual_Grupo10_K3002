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
        super(null, descripcion, subcategoria, cantidadObjetivo, cantidadRecibida, entidad);
    }

    public LocalDate getSatisfechaEn() {
        return LocalDate.now();
    }

    @Override
    public boolean estaSatisfecha() {

        return getCantidadRecibida() >= getCantidadObjetivo();
    }
}