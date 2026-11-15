package com.grupo10.servicio_donaciones.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedioContactoRequestDTO {
    private String tipo;
    private String correoElectronico;
    private String telefono;
    private boolean esPredeterminado;
}