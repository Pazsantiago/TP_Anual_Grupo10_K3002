package org.example.broker.dominio.donacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DonacionSegmentadaDTO {
    private Long idSegmentada;
    private Integer cantidad;
    private UnidadMedida unidad;
}
