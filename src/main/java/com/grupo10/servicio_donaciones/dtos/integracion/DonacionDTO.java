package com.grupo10.servicio_donaciones.dtos.integracion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonacionDTO {
    private Long id;
    private Date fechaEntrada = new Date();
    private List<String> categoriasIncluidas = new ArrayList<>();
    private Integer cantBienes;
    private List<Long> segmentadasIncluidas;

    public Integer cantidadSegmentadasEntregadas() {
        return segmentadasIncluidas.size();
    }
}