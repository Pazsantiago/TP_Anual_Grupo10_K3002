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
public class BienAsignado {
    private Long id;
    private Integer cantidadEntregada;
    private Long idNecesidadAsignada;
    @JsonIgnore
    private DonacionSegmentada donacionSegmentada;

    public BienAsignado(Integer cantidadEntregada, Long idNecesidadAsignada, DonacionSegmentada donacionSegmentada) {
        this.cantidadEntregada = cantidadEntregada;
        this.idNecesidadAsignada = idNecesidadAsignada;
        this.donacionSegmentada = donacionSegmentada;
    }
}
