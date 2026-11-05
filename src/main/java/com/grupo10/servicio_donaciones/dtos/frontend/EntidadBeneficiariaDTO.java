package com.grupo10.servicio_donaciones.dtos.frontend;

import lombok.Data;

@Data
public class EntidadBeneficiariaDTO {
    private Long id;
    private String razonSocial;
    private String direccion;
    private String telefono;
    private String correoRepresentante;
}