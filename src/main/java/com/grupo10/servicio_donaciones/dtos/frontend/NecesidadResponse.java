package com.grupo10.servicio_donaciones.dtos.frontend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NecesidadResponse(
        Long id,
        Long idEntidadBeneficiaria,
        String descripcion,
        Integer cantidadObjetivo,
        Boolean estaSatisfechaConDonacion) {
}
