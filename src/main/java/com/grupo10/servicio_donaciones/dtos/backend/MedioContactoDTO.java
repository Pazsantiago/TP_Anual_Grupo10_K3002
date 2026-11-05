package com.grupo10.servicio_donaciones.dtos.backend;

import com.grupo10.servicio_donaciones.donaciones.dominio.donante.MedioNotificacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MedioContactoDTO {
    private MedioNotificacion tipo;
    private String correoElectronico;
    private String telefono;
}