package com.grupo10.servicio_donaciones.dtos.backend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DonacionAsignadaDTO {
    private Long idAsignada;
    private List<DonacionSegmentadaDTO> donacionesSegmentadas;
    private Long idEntidad;
    private String direccionEntidad;
}
