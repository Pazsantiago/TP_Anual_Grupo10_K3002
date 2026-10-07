package com.grupo10.servicio_donaciones.Sdonaciones.dominio.necesidad;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.categoria.Subcategoria;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
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


    public LocalDate getSatisfechaEn() {
        return LocalDate.now();
    }

    @Override
    public boolean estaSatisfecha() {

        return this.getCantidadRecibida() >= this.getCantidadObjetivo() && this.estaSatisfecha();
    }
}