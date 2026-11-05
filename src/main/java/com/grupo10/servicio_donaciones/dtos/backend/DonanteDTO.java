package com.grupo10.servicio_donaciones.dtos.backend;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class DonanteDTO {
    private Long id;
    private String nombre;
    private List<DonacionDTO> donaciones = new ArrayList<>();
    private MedioContactoDTO contactoPredeterminado;
}
