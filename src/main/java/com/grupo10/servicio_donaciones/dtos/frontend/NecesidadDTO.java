package com.grupo10.servicio_donaciones.dtos.frontend;

import lombok.Data;

@Data
public class NecesidadDTO {
    private Long id;
    private Long idEntidadBeneficiaria;
    private String descripcion;
    private Integer cantidadObjetivo;
    private Boolean estaSatisfechaConDonacion;
}
