package com.grupo10.servicio_donaciones.dtos.frontend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DonacionSegmentadaResponse(
        Long id,
        Long donacionInicial,
        String estadoActual,
        String subcategoria
) {
}



