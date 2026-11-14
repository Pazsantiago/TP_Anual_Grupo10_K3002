package com.grupo10.servicio_donaciones.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedioContactoResponseDTO {
    private String tipo;
    private String correoElectronico;
    private String telefono;
}