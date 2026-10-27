package com.grupo10.servicio_donaciones.Sdonaciones.mensajes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MensajeInicioDeRuta {
    private Long idAsignada;
    private List<Long> idDonantesIncluidos = new ArrayList<>();
    ;
}
