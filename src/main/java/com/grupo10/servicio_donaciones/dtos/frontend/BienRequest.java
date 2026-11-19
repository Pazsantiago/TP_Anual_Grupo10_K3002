package com.grupo10.servicio_donaciones.dtos.frontend;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

/**
 * fotoBase64 / fotoTipo (ej. image/jpeg): foto opcional del bien, embebida en el JSON de la carga.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BienRequest(
        String descripcion, String categoria, String subcategoria, Integer cantidad, String unidad,
        String estado, Boolean perecedero, LocalDate fechaVencimiento,
        String fotoBase64, String fotoTipo) {
}
