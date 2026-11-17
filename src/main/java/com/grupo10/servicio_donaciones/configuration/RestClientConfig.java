package com.grupo10.servicio_donaciones.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {
    @Value("${conexiones.broker-logistica}")
    private String urlLogistica;

    @Value("${conexiones.servicio-incentivos}")
    private String urlIncentivos;

    @Value("${conexiones.cola-de-mensajes}")
    private String urlMensajes;

    private final JwtInterceptor jwtInterceptor;

    @Bean
    @Qualifier("restClientBrokerLogistica")
    public RestClient restClientBroker(RestClient.Builder builder) {
        return builder
                .baseUrl(urlLogistica)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .requestInterceptor(jwtInterceptor)
                .build();
    }

    @Bean
    @Qualifier("restClientIncentivos")
    public RestClient restClientIncentivos(RestClient.Builder builder) {
        return builder
                .baseUrl(urlIncentivos)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .requestInterceptor(jwtInterceptor)
                .build();
    }

    @Bean
    @Qualifier("restClientMensajes")
    public RestClient restClientMensajes(RestClient.Builder builder) {
        return builder
                .baseUrl(urlMensajes)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .requestInterceptor(jwtInterceptor)
                .build();
    }
}
