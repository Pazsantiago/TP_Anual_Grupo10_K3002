package org.example.broker.dominio.mensajes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MensajeConfirmacionIncentivos {
    private Long idAsignada;
    private List<Long> idDonantesIncluidos;
    private List<Long> idSegmentadasIncluidos;
    private Long idEntidad;
}

