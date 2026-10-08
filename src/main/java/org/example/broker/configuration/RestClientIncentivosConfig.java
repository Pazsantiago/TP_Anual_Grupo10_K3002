package org.example.broker.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientIncentivosConfig {
    @Value("${conexiones.servicio-incentivos}")
    private String urlIncentivos;

    @Bean
    @Qualifier("restClientIncentivos")
    public RestClient restClientIncentivos(RestClient.Builder builder) {
        return builder
                .baseUrl(urlIncentivos)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
