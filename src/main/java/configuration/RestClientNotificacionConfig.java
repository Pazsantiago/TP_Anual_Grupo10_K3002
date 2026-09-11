package configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientNotificacionConfig {
        @Value("${conexiones.servicio-notificaciones}")
        private String urlNotificaciones;

        @Bean
        @Qualifier("restClientNotificaciones")
        public RestClient restClient() {
            return RestClient.builder()
                    .baseUrl(urlNotificaciones)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();
        }
}
