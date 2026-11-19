package com.grupo10.servicio_donaciones.dtos.frontend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Una entidad propuesta por un algoritmo de asignacion. algoritmoUtilizado: COMPATIBILIDAD_SEMANTICA o PRIORIDAD_SUB_ATENDIDOS.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RankingEntidadResponse(Long idEntidad, Integer posicion, Double puntaje, String algoritmoUtilizado) {
}
