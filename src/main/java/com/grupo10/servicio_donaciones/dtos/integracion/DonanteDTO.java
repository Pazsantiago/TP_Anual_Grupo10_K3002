package com.grupo10.servicio_donaciones.dtos.integracion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonanteDTO {
    private Long id;
    private String nombre;
    private List<DonacionDTO> donaciones = new ArrayList<>();
    private MedioContactoDTO contactoPredeterminado;
}