package com.grupo10.servicio_donaciones.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaRequestDTO {
    private String tipo;
    private String nombre;
    private Integer edad;
    private String genero;
    //
    private String razonSocial;
    private String tipoJuridica;
    private String rubro;
    private List<Long> representantes = new ArrayList<>();
}
