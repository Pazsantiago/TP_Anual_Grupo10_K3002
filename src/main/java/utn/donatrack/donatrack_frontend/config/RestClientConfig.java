package utn.donatrack.donatrack_frontend.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class RestClientConfig {

    @Value("${donatrack.services.donaciones.base-url:http://localhost:8080}")
    private String donacionesUrl;

    @Value("${donatrack.services.incentivos.base-url:http://localhost:8091}")
    private String incentivosUrl;

    @Value("${donatrack.services.logistica.base-url:http://localhost:8092}")
    private String logisticaUrl;

    @Value("${donatrack.services.notificaciones.base-url:http://localhost:8081}")
    private String notificacionesUrl;

    // 1. Cliente para Donaciones
    @Bean(name = {"donacionesRestClient", "restClientDonaciones"})
    public RestClient restClientDonaciones() {
        return crearRestClient(donacionesUrl);
    }

    // 2. Cliente para Incentivos
    @Bean(name = {"incentivosRestClient", "restClientIncentivos"})
    public RestClient restClientIncentivos() {
        return crearRestClient(incentivosUrl);
    }

    // 3. Cliente para Logistica
    @Bean(name = {"logisticaRestClient", "restClientLogistica"})
    public RestClient restClientLogistica() {
        return crearRestClient(logisticaUrl);
    }

    // 4. Cliente para Notificaciones
    @Bean(name = {"notificacionesRestClient", "restClientNotificaciones"})
    public RestClient restClientNotificaciones() {
        return crearRestClient(notificacionesUrl);
    }

    private RestClient crearRestClient(String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestInterceptor((request, body, execution) -> {
                    ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    if (attrs != null) {
                        HttpSession session = attrs.getRequest().getSession(false);
                        if (session != null) {
                            String token = (String) session.getAttribute("jwtToken");
                            if (token != null && !token.isBlank()) {
                                request.getHeaders().add("Authorization", "Bearer " + token);
                            }
                        }
                    }
                    return execution.execute(request, body);
                })
                .build();
    }
}