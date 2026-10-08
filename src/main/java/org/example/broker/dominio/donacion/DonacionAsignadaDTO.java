package org.example.broker.dominio.donacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class DonacionAsignadaDTO {
    private Long id;
    private String direccionEntidad;
    private List<DonacionSegmentadaDTO> donacionesSegmentadas;
    private Long necesidadResuelta;
}
