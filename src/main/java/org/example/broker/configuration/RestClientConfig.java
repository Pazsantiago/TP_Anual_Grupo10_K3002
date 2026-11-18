package org.example.broker.configuration;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class RestClientConfig {
    @Value("${conexiones.servicio-donaciones}")
    private String urlMensajes;

    @Value("${conexiones.servicio-incentivos}")
    private String urlIncentivos;

    @Value("${conexiones.servicio-logistica-online}")
    private String urlLogistica;

    @Bean
    @Qualifier("restClientDonaciones")
    public RestClient restClientDonaciones() {
        return RestClient.builder()
                .baseUrl(urlMensajes)
                .requestInterceptor((request, body, execution) -> {
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
                        request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue());
                    }

                    return execution.execute(request, body);
                })
                .build();
    }

    @Bean
    @Qualifier("restClientIncentivos")
    public RestClient restClientIncentivos() {
        return RestClient.builder()
                .baseUrl(urlIncentivos)
                .requestInterceptor((request, body, execution) -> {
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
                        request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue());
                    }

                    return execution.execute(request, body);
                })
                .build();
    }

    @Bean
    @Qualifier("restClientLogistica")
    public RestClient restClientLogistica() {
        return RestClient.builder()
                .baseUrl(urlLogistica)
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
