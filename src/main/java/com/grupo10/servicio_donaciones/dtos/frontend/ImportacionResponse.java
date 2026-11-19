package com.grupo10.servicio_donaciones.dtos.frontend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Resumen de POST /api/donantes/importar.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ImportacionResponse(Integer total, Integer creados, Integer actualizados, Integer errores,
                                  List<ErrorImportacion> detalleErrores) {
    public ImportacionResponse {
        if (detalleErrores == null) detalleErrores = List.of();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ErrorImportacion(Integer linea, String motivo) {
    }
}
