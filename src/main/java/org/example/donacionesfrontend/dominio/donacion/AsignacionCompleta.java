package org.example.donacionesfrontend.dominio.donacion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AsignacionCompleta {
    private Long id;
    private Integer nroComprobante;
    private Long idCamionAsignado;
    private Date fecha = new Date();

    public AsignacionCompleta(Integer nroComprobante, Long idCamionAsignado) {
        this.nroComprobante = nroComprobante;
        this.idCamionAsignado = idCamionAsignado;
    }
}

