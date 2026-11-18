package org.example.coladenotificaciones.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
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
                        .requestInterceptor((request, body, execution) -> {
                                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                                if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
                                        request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue());
                                }

                                return execution.execute(request, body);
                        })
                        .build();
        }
}
