package utn.donatrack.donatrack_frontend.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/** Donde vive el Servicio de Notificaciones. Se enlaza con donatrack.services.notificaciones.* */
@ConfigurationProperties(prefix = "donatrack.services.notificaciones")
public record NotificacionesProperties(String baseUrl, Duration connectTimeout, Duration readTimeout) {

    public NotificacionesProperties {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalArgumentException(
                "Falta donatrack.services.notificaciones.base-url (o la variable NOTIFICACIONES_URL)");
        }
        if (connectTimeout == null) connectTimeout = Duration.ofSeconds(2);
        if (readTimeout == null) readTimeout = Duration.ofSeconds(5);
    }
}
