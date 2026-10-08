package org.example.broker.dominio.mensajes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class MensajeConfirmacionEntrega {
    private Long idEntidadBeneficiaria;
    private List<Long> idDonantesIncluidos;
    private Integer nroComprobante;
    private Date fecha;
    private Long idNecesidad;
    private Long idCamionAsignado;

    public MensajeConfirmacionEntrega(Long idEntidadBeneficiaria, List<Long> idDonantesIncluidos, Integer nroComprobante, Long idNecesidad, Long idCamionAsignado) {
        this.idEntidadBeneficiaria = idEntidadBeneficiaria;
        this.idDonantesIncluidos = idDonantesIncluidos;
        this.nroComprobante = nroComprobante;
        this.fecha = new Date();
        this.idNecesidad = idNecesidad;
        this.idCamionAsignado = idCamionAsignado;
    }
}
