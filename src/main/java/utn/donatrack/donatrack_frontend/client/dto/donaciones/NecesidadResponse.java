package utn.donatrack.donatrack_frontend.client.dto.donaciones;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NecesidadResponse(
    Long id,
    Long idEntidadBeneficiaria,
    String descripcion,
    Integer cantidadObjetivo,
    Boolean estaSatisfechaConDonacion) {}
