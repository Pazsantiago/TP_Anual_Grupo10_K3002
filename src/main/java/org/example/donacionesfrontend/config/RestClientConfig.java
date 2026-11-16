package org.example.donacionesfrontend.config;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Value("${backend.servicio-donaciones.url}")
    private String donacionesUrl;

    @Bean
    @Qualifier("restClientDonaciones")
    public RestClient restClientDonaciones(HttpSession session) {
        return RestClient.builder()
                .baseUrl(donacionesUrl)
                .requestInterceptor((request, body, execution) -> {
                    String token = (String) session.getAttribute("jwtToken");
                    if (token != null) {
                        request.getHeaders().add("Authorization", "Bearer " + token);
                    }
                    return execution.execute(request, body);
                })
                .build();
    }

}