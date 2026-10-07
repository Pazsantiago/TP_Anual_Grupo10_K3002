package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.categoria.Subcategoria;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DonacionSegmentadaDTO {
    private Long id;
    private TipoEstadoDonacion tipoEstado;
    private Subcategoria subcategoria;
    private Long idNecesidad;
    private Integer cantidadBienAsignado;
}
