package com.grupo10.servicio_donaciones.dtos.integracion;

import com.grupo10.servicio_donaciones.donaciones.dominio.bien.UnidadMedida;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonacionSegmentadaDTO {
    private Long idSegmentada;
    private Integer cantidad;
    private UnidadMedida unidad;
}