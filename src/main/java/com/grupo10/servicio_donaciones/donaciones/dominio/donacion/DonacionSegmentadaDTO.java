package com.grupo10.servicio_donaciones.donaciones.dominio.donacion;

import com.grupo10.servicio_donaciones.donaciones.dominio.bien.UnidadMedida;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DonacionSegmentadaDTO {
    private Long idSegmentada;
    private Integer cantidad;
    private UnidadMedida unidad;
}
