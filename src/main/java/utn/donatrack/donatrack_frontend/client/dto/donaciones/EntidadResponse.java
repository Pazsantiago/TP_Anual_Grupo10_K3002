package utn.donatrack.donatrack_frontend.client.dto.donaciones;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EntidadResponse(Long id, String razonSocial, String direccion, String telefono, String correoRepresentante) {}
