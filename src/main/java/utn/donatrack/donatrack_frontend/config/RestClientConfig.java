package utn.donatrack.donatrack_frontend.config;

import java.net.http.HttpClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(NotificacionesProperties.class)
public class RestClientConfig {

    /** Tiene que haber Un RestClient por servicio externo:
     * cada uno con su propia base URL y timeouts. */
    @Bean
    RestClient notificacionesRestClient(NotificacionesProperties props) {
        HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(props.connectTimeout())
            .build();
        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(props.readTimeout());

        return RestClient.builder()
            .baseUrl(props.baseUrl())
            .requestFactory(factory)
            .build();
    }
}
