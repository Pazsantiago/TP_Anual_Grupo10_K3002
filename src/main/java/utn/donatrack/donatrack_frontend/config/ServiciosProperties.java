package utn.donatrack.donatrack_frontend.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Donde vive cada servicio de dominio. Se enlaza con donatrack.services.{donaciones|incentivos|logistica}.*
 * (Notificaciones tiene su propia clase: NotificacionesProperties).
 *
 * Todo es opcional: si no se configura nada, se usan los puertos locales por defecto.
 * Para cambiarlo, en application.properties o con variables de entorno:
 *   donatrack.services.donaciones.base-url=${DONACIONES_URL:http://localhost:8090}
 *   donatrack.services.incentivos.base-url=${INCENTIVOS_URL:http://localhost:8091}
 *   donatrack.services.logistica.base-url=${LOGISTICA_URL:http://localhost:8092}
 *   donatrack.services.<servicio>.connect-timeout=2s
 *   donatrack.services.<servicio>.read-timeout=10s
 */
@ConfigurationProperties(prefix = "donatrack.services")
public record ServiciosProperties(Endpoint donaciones, Endpoint incentivos, Endpoint logistica) {

    public ServiciosProperties {
        donaciones = Endpoint.conDefecto(donaciones, "http://localhost:8090");
        incentivos = Endpoint.conDefecto(incentivos, "http://localhost:8091");
        logistica = Endpoint.conDefecto(logistica, "http://localhost:8092");
    }

    /** URL base y timeouts de un servicio. */
    public record Endpoint(String baseUrl, Duration connectTimeout, Duration readTimeout) {

        public Endpoint {
            if (connectTimeout == null) connectTimeout = Duration.ofSeconds(2);
            if (readTimeout == null) readTimeout = Duration.ofSeconds(10);
        }

        static Endpoint conDefecto(Endpoint configurado, String baseUrlPorDefecto) {
            if (configurado == null) {
                return new Endpoint(baseUrlPorDefecto, null, null);
            }
            if (configurado.baseUrl() == null || configurado.baseUrl().isBlank()) {
                return new Endpoint(baseUrlPorDefecto, configurado.connectTimeout(), configurado.readTimeout());
            }
            return configurado;
        }
    }
}
