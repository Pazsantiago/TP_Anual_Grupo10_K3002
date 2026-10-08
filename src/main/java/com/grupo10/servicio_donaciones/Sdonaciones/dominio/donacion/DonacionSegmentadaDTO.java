package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DonacionSegmentadaDTO {
    private Long id;
    private Long donanteId;
}
