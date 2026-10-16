package org.example.broker.dominio.mensajes;

import lombok.*;

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
