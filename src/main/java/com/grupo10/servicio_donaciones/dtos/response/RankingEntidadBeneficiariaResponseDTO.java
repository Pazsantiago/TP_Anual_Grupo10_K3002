package com.grupo10.servicio_donaciones.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankingEntidadBeneficiariaResponseDTO {
    private EntidadBeneficiariaResponseDTO entidadBeneficiaria;
    private Integer posicion;
    private double puntaje;
    private String algoritmoUsado;

}