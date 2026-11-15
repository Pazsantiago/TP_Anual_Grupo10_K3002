package org.example.donacionesfrontend.dtos.request;

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
