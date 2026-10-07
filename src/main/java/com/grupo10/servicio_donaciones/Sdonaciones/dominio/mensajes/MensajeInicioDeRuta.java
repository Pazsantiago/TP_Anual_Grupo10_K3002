package com.grupo10.servicio_donaciones.Sdonaciones.dominio.mensajes;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MensajeInicioDeRuta {
    private Long idEntidadBeneficiaria;
    private List<Long> idDonantesIncluidos;
    private Long idNecesidad;
}
