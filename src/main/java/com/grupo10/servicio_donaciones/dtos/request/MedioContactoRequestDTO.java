package com.grupo10.servicio_donaciones.dtos.request;

import com.grupo10.servicio_donaciones.donaciones.dominio.donante.MedioNotificacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedioContactoRequestDTO {
    private MedioNotificacion tipo;
    private String correoElectronico;
    private String telefono;
    private boolean esPredeterminado;
}