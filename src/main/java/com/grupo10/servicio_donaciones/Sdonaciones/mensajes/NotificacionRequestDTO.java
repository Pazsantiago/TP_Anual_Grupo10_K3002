package com.grupo10.servicio_donaciones.Sdonaciones.mensajes;


import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante.MedioNotificacion;
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

    public String getAsunto() {
        return asunto;
    }

    public MedioNotificacion getMedioNotificacion() {
        return medioNotificacion;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public DestinatarioRequestDTO getDestinatario() {
        return destinatario;
    }

    public String getEventoOrigen() {
        return eventoOrigen;
    }

    public void setMedioNotificacion(MedioNotificacion medioNotificacion) {
        this.medioNotificacion = medioNotificacion;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public void setDestinatario(DestinatarioRequestDTO destinatario) {
        this.destinatario = destinatario;
    }

    public void setEventoOrigen(String eventoOrigen) {
        this.eventoOrigen = eventoOrigen;
    }

}
