package utn.donatrack.donatrack_frontend.client.dto.incentivos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PerfilMetricasResponse(Long id, String categoriaActual, Integer cantidadInsignias, Integer misionesCompletadas) {}
