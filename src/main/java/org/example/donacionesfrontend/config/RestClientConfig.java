package org.example.donacionesfrontend.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${backend.servicio-donaciones.url}")
    private String backendUrl;

    @Bean
    public RestClient restClientDonaciones() {
        return RestClient.builder()
                .baseUrl(backendUrl)
                .build();
    }
}
