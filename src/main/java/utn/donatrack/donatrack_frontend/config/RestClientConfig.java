package utn.donatrack.donatrack_frontend.config;

import java.net.http.HttpClient;
import java.time.Duration;

import jakarta.servlet.http.HttpSession;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@EnableConfigurationProperties({NotificacionesProperties.class, ServiciosProperties.class})
public class RestClientConfig {

    /** Un RestClient por servicio externo: cada uno con su propia base URL y timeouts.
     *  Para sumar un servicio: agregar su Endpoint en ServiciosProperties y un @Bean aca. */
    @Bean
    RestClient notificacionesRestClient(NotificacionesProperties props) {
        return crear(props.baseUrl(), props.connectTimeout(), props.readTimeout());
    }

    @Bean
    RestClient donacionesRestClient(ServiciosProperties props) {
        return crear(props.donaciones());
    }

    @Bean
    RestClient incentivosRestClient(ServiciosProperties props) {
        return crear(props.incentivos());
    }

    @Bean
    RestClient logisticaRestClient(ServiciosProperties props) {
        return crear(props.logistica());
    }

    private static RestClient crear(ServiciosProperties.Endpoint endpoint) {
        return crear(endpoint.baseUrl(), endpoint.connectTimeout(), endpoint.readTimeout());
    }

    private static RestClient crear(String baseUrl, Duration connectTimeout, Duration readTimeout) {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(connectTimeout)
                .build();
        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(readTimeout);

        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory) // Configura timeouts
                .requestInterceptor((request, body, execution) -> {
                    // Obtenemos la sesión HTTP de la solicitud actual de forma limpia
                    ServletRequestAttributes attributes =
                            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                    if (attributes != null) {
                        HttpSession session = attributes.getRequest().getSession(false);
                        if (session != null) {
                            String token = (String) session.getAttribute("jwtToken");
                            if (token != null) {
                                // Inyecta el encabezado Authorization: Bearer <token>
                                request.getHeaders().add("Authorization", "Bearer " + token);
                            }
                        }
                    }
                    return execution.execute(request, body);
                })
                .build();
    }
}

