package org.example.broker.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientDonacionesConfig {
    @Value("${conexiones.servicio-donaciones}")
    private String urlMensajes;

    @Bean
    @Qualifier("restClientDonaciones")
    public RestClient restClientDonaciones(RestClient.Builder builder) {
        return builder
                .baseUrl(urlMensajes)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
