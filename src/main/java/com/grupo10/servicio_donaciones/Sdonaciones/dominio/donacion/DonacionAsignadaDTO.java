package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DonacionAsignadaDTO {
    private Long id;
    private String direccionEntidadBeneficiaria;
    private List<DonacionSegmentadaDTO> donacionSegmentadas;
}
