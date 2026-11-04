package com.grupo10.servicio_donaciones.donaciones.mensajes;


import com.grupo10.servicio_donaciones.donaciones.dominio.donante.MedioNotificacion;
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
