package com.grupo10.servicio_donaciones.dtos.backend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
