package org.example.donacionesfrontend.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NecesidadRequestDTO {
    private String tipo;
    private String descripcion;
    private SubcategoriaRequestDTO subcategoria;
    private Integer cantidadObjetivo;
    private Integer cantidadRecibida = 0;
    private EntidadBeneficiariaRequestDTO entidadBeneficiaria;
    private Boolean estaSatisfechaConDonacion = false;
    private LocalDate satisfechaEn;

    //
    private PeriodoRequestDTO periodoRequestDTO;
    private Integer cantidadRecibidaEnPeriodo = 0;
}
