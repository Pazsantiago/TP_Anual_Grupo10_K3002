package com.grupo10.servicio_donaciones.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonacionSegmentadaResponseDTO {
    private Long idSegmentada;
    private Integer cantidadOriginal;
    private Integer cantidadActual;
    private String estadoActual;
    private String unidad;
}