package configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientBrokerConfig {
    @Value("${conexiones.broker-logistica}")
    private String urlLogistica;

    @Bean
    @Qualifier("restClientBrokerLogistica")
    public RestClient restClientBroker(RestClient.Builder builder) {
        return builder
                .baseUrl(urlLogistica)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
