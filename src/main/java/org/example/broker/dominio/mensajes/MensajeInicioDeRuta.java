package org.example.broker.dominio.mensajes;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MensajeInicioDeRuta {
    private Long idAsignada;
    private List<Long> idDonantesIncluidos;
}
