package com.grupo10.servicio_donaciones.dtos.integracion;

import com.grupo10.servicio_donaciones.donaciones.dominio.donante.MedioNotificacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedioContactoDTO {
    private MedioNotificacion tipo;
    private String correoElectronico;
    private String telefono;
}