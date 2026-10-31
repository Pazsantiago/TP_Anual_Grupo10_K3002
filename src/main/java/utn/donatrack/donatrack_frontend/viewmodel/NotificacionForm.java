package utn.donatrack.donatrack_frontend.viewmodel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import utn.donatrack.donatrack_frontend.client.dto.DestinatarioRequest;
import utn.donatrack.donatrack_frontend.client.dto.NotificacionRequest;

/** Modelo del formulario (plano, pensado para la vista). Se convierte al DTO de la API al enviar. */
@Getter
@Setter
public class NotificacionForm {

    @NotNull(message = "Elegí un medio de envío")
    private MedioVista medio;

    private String asunto;

    @NotBlank(message = "Escribí el mensaje")
    private String cuerpo;

    @NotBlank(message = "Indicá el nombre de la persona destinataria")
    private String nombre;

    private String email;
    private String telefono;
    private String whatsapp;

    public NotificacionRequest toRequest() {
        return new NotificacionRequest(
            medio.name(),
            vacioANull(asunto),
            cuerpo,
            new DestinatarioRequest(nombre, vacioANull(telefono), vacioANull(email), vacioANull(whatsapp)),
            null);
    }

    private static String vacioANull(String s) {
        return (s == null || s.isBlank()) ? null : s.trim();
    }
}
