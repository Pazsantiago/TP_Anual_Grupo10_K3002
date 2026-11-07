package utn.donatrack.donatrack_frontend.client.dto.logistica;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DireccionResponse(String calle, String ciudad) {}
