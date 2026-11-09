package org.example.broker.dominio.mensajes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
public class MensajeConfirmacionIncentivosDTO {
    private Long idAsignada;
    private Long idEntidad;
    private List<Long> idSegmentadasIncluidos;
}