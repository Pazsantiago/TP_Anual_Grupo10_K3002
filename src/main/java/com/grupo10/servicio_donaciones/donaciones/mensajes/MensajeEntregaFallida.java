package com.grupo10.servicio_donaciones.donaciones.mensajes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MensajeEntregaFallida {
    private Long idAsignada;
    private String justificacion;
    private Boolean sePuedeReplanificar;
}
