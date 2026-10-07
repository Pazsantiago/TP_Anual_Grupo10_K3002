package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AsignacionCompleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer nroComprobante;
    private Long idCamionAsignado;
    private Date fecha;

    public AsignacionCompleta(Integer nroComprobante, Long idCamionAsignado) {
        this.nroComprobante = nroComprobante;
        this.idCamionAsignado = idCamionAsignado;
        this.fecha = new Date();
    }
}

