package utn.donatrack.donatrack_frontend.client.dto.logistica;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** peso en kg, volumen en m3, altura en metros. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BultoResponse(Long id, Double peso, Double volumen, Double altura) {}
