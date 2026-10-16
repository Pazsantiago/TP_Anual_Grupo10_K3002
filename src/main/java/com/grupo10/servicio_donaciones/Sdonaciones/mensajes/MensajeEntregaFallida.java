package com.grupo10.servicio_donaciones.Sdonaciones.mensajes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MensajeEntregaFallida {
    private Long idAsignada;
    private List<Long> idDonantesIncluidos;
    private String justificacion;
    private Boolean sePuedeReplanificar;
}
