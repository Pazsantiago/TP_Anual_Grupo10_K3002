package utn.donatrack.donatrack_frontend.client.dto.donaciones;

import com.fasterxml.jackson.annotation.JsonInclude;

/** Cuerpo de POST /api/entidadesBeneficiarias/{id}/necesidades. tipo: RECURRENTE o EXTRAORDINARIA; periodo solo en recurrentes. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record NecesidadRequest(String tipo, String subcategoria, String descripcion, Integer cantidadObjetivo, String periodo) {}
