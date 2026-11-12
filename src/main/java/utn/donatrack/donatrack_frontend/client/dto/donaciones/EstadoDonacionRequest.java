package utn.donatrack.donatrack_frontend.client.dto.donaciones;

import com.fasterxml.jackson.annotation.JsonInclude;

/** Cuerpo de PATCH /api/donaciones/{id}/estado. La justificacion es opcional (se pide en fallos de entrega). */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record EstadoDonacionRequest(String estado, String justificacion) {}
