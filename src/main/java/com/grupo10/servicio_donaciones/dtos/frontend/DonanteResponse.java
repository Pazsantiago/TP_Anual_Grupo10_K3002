package com.grupo10.servicio_donaciones.dtos.frontend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Espejo de dtos.frontend.DonanteDTO del Servicio de Donaciones. Lector tolerante: ignora campos nuevos.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DonanteResponse(
        Long id,
        String tipoPersona,        // "HUMANA" o "JURIDICA"
        String nombre,
        String apellido,
        String tipoDocumento,
        String documento,
        String razonSocial,
        String rubro,
        String correoElectronico,
        String telefono) {
}
