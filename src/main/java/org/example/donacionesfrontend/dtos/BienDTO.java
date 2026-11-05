package org.example.donacionesfrontend.dtos;

import lombok.Data;

@Data
public class BienDTO {
    private String descripcion;
    private Integer cantidadOriginal;
    private String unidad; // "KILOGRAMOS", "UNIDADES", etc.
}