package utn.donatrack.donatrack_frontend.client.dto.incentivos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** Posicion del ranking mensual: id del donante y misiones completadas (puntaje). */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PosicionRankingResponse(Long id, Integer puntaje) {}
