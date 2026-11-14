package com.grupo10.servicio_donaciones.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonacionResponseDTO {
    private Long id;
    private Date fechaEntrada;
    private Integer cantBienes;
    private List<String> categoriasIncluidas = new ArrayList<>();
    private List<Long> segmentadasIncluidas = new ArrayList<>();
}