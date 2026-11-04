package com.grupo10.servicio_donaciones.donaciones.mensajes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Setter
@Getter
public class MensajeConfirmacionEntrega {
    private Long idAsignada;
    private Integer nroComprobante;
    private Date fecha = new Date();
    private Long idCamionAsignado;
}
