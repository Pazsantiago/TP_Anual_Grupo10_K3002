package com.grupo10.servicio_donaciones.dtos.frontend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EntidadResponse(Long id, String razonSocial, String direccion, String telefono,
                              String correoRepresentante) {
}
