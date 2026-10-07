package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(exclude = {"donacionSegmentada"})
@Entity
@NoArgsConstructor
public class EstadoDonacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TipoEstadoDonacion tipoEstado;
    private String justificacion;
    private Boolean esHistorico = false;
    @ManyToOne()
    @JsonIgnore
    private DonacionSegmentada donacionSegmentada;

    public EstadoDonacion(TipoEstadoDonacion tipoEstado, String justificacion, DonacionSegmentada donacionSegmentada) {
        this.tipoEstado = tipoEstado;
        this.justificacion = justificacion;
        this.donacionSegmentada = donacionSegmentada;
    }
}
