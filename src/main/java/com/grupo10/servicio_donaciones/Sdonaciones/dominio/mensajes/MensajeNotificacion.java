package com.grupo10.servicio_donaciones.Sdonaciones.dominio.mensajes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MensajeNotificacion {
    @JsonProperty("eventoOrigen")
    private TipoEvento tipoEvento;
    private Destinatario destinatario;
    private String cuerpo;
    private String asunto;
}
