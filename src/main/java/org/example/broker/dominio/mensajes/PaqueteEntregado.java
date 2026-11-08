package org.example.broker.dominio.mensajes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PaqueteEntregado {
    private Long idAsignada;
    private Long idEntidad;
    private List<Long> idSegmentadasIncluidos;
}

