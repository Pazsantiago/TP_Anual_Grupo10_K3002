package com.grupo10.servicio_donaciones.dtos.request;

import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.TipoEstadoDonacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoDonacionRequestDTO {
    private TipoEstadoDonacion tipoEstado;
    private String justificacion;
}
