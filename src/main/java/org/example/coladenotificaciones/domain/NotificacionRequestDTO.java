package org.example.coladenotificaciones.domain;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificacionRequestDTO {
    @NotNull(message = "El medio de notificación es obligatorio")
    MedioNotificacion medioNotificacion;
    String cuerpo;
    String asunto;
    DestinatarioRequestDTO destinatario;
    String eventoOrigen;
}
