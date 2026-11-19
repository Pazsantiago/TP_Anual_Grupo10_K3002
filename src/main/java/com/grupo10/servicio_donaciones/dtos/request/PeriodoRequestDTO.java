package com.grupo10.servicio_donaciones.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeriodoRequestDTO {
    private Integer periodoDias;
    private LocalDate inicioPeriodo;
}