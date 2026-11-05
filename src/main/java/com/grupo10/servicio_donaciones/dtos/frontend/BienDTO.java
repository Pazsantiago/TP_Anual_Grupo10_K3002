package com.grupo10.servicio_donaciones.dtos.frontend;

import lombok.Data;

@Data
public class BienDTO {
    private String descripcion;
    private Integer cantidadOriginal;
    private String unidad; // "KILOGRAMOS", "UNIDADES", etc.
}