package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MensajeNotificacion {
    private TipoEvento tipoEvento;
    private Destinatario destinatario;
    private String cuerpo;

}
