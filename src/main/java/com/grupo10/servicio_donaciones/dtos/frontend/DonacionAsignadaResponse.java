package com.grupo10.servicio_donaciones.dtos.frontend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DonacionAsignadaResponse(
        Long id,
        List<Long> donacionesSegmentadas,
        Long necesidadResuelta
) {
}
