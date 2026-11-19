package com.grupo10.servicio_donaciones.dtos.frontend;

/**
 * Cuerpo de POST /api/rankings/{idSegmentada}/asignacion: la entidad final que eligio la persona administradora.
 */
public record AsignacionRequest(Long idEntidad) {
}
