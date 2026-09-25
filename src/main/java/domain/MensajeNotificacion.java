package domain;

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

    // faltaba
    private MedioNotificacion medioNotificacion;

    @JsonProperty("eventoOrigen")
    private TipoEvento tipoEvento;

    private Destinatario destinatario;
    private String cuerpo;

    //  opcional si es un correo electrónico
    private String asunto;
}
