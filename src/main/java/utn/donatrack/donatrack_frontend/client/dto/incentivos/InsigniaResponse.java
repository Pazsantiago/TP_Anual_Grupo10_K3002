package utn.donatrack.donatrack_frontend.client.dto.incentivos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record InsigniaResponse(Long id, String nombre, String descripcion) {}
