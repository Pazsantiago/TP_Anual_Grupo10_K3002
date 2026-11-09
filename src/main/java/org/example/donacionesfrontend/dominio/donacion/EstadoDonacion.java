package org.example.donacionesfrontend.dominio.donacion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(exclude = {"donacionSegmentada"})
@NoArgsConstructor
public class EstadoDonacion {
    private Long id;
    private TipoEstadoDonacion tipoEstado;
    private String justificacion;
    private Boolean esHistorico = false;
    @JsonIgnore
    private DonacionSegmentada donacionSegmentada;

    public EstadoDonacion(TipoEstadoDonacion tipoEstado, String justificacion, DonacionSegmentada donacionSegmentada) {
        this.tipoEstado = tipoEstado;
        this.justificacion = justificacion;
        this.donacionSegmentada = donacionSegmentada;
    }
}
