package com.grupo10.servicio_donaciones.Sdonaciones.dominio.mensajes;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MensajeEntregaFallida {
    private Long idEntidadBeneficiaria;
    private List<Long> idDonantesIncluidos;
    private String justificacion;
    private Long idNecesidad;
    private Boolean sePuedeReplanificar;
}
