package com.grupo10.servicio_donaciones.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NecesidadResponseDTO {
    private Long id;
    private String descripcion;
    private Integer cantidadObjetivo;
    private Integer cantidadAsignada;
    private Boolean estaSatisfechaConDonacion;
    private Long idEntidadBeneficiaria;
}