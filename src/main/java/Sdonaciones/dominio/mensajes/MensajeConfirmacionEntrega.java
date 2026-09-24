package Sdonaciones.dominio.mensajes;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class MensajeConfirmacionEntrega {
    private Long idEntidadBeneficiaria;
    private List<Long> idDonantesIncluidos;
    private Integer nroComprobante;
    private Date fecha;
    private Long idNecesidad;
    private Long idCamionAsignado;
}
