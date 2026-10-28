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
public class MensajeConfirmacionEntrega {
    private Long idAsignada;
    private List<Long> idDonantesIncluidos;
    private List<Long> idSegmentadasIncluidos;
    private Long idEntidad;
    private Integer nroComprobante;
    private Date fecha;
    private Long idCamionAsignado;

}
