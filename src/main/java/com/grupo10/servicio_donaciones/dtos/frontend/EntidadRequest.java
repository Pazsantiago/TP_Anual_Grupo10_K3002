package com.grupo10.servicio_donaciones.dtos.frontend;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Cuerpo de POST /api/entidadesBeneficiarias. "clave" solo viaja en el auto-registro.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record EntidadRequest(
        String razonSocial, String tipoEntidad, String telefono,
        String calle, String numero, String ciudad, String provincia, String codigoPostal,
        String representanteNombre, String correoRepresentante, String clave) {
}
