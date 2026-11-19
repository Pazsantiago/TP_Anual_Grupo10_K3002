package com.grupo10.servicio_donaciones.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonanteResponseDTO {
    private Long id;
    private String nombre;
    private MedioContactoResponseDTO contactoPredeterminado;
    private List<DonacionResponseDTO> donaciones = new ArrayList<>();
}