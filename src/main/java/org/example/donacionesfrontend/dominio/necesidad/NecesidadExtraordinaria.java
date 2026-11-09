package org.example.donacionesfrontend.dominio.necesidad;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.donacionesfrontend.dominio.categoria.Subcategoria;
import org.example.donacionesfrontend.dominio.entidad.EntidadBeneficiaria;

@Getter
@Setter
@NoArgsConstructor
public class NecesidadExtraordinaria extends Necesidad {

    public NecesidadExtraordinaria(
            String descripcion,
            Subcategoria subcategoria,
            Integer cantidadObjetivo,
            Integer cantidadRecibida,
            EntidadBeneficiaria entidad,
            Boolean estaSatisfecha
    ) {
        super(descripcion, subcategoria, cantidadObjetivo, cantidadRecibida, entidad, estaSatisfecha);
    }


    @Override
    public boolean estaSatisfecha() {

        return this.getCantidadRecibida() >= this.getCantidadObjetivo() && this.estaSatisfecha();
    }
}