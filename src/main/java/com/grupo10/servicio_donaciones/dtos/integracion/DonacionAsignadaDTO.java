package com.grupo10.servicio_donaciones.dtos.integracion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonacionAsignadaDTO {
    private Long idAsignada;
    private List<DonacionSegmentadaDTO> donacionesSegmentadas;
    private Long idEntidad;
    private String direccionEntidad;
}