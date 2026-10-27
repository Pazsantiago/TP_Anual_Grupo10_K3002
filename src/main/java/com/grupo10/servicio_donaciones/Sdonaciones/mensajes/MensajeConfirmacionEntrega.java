package com.grupo10.servicio_donaciones.Sdonaciones.mensajes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class MensajeConfirmacionEntrega {
    private Long idAsignada;
    private List<Long> idDonantesIncluidos = new ArrayList<>();
    ;
    private Integer nroComprobante;
    private Date fecha = new Date();
    private Long idCamionAsignado;
}
