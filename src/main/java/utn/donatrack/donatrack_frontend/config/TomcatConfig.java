package utn.donatrack.donatrack_frontend.config;

import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainerCustomizer() {
        return factory -> factory.addConnectorCustomizers(connector ->
                // maxLocaleCount es un atributo del Connector (no del protocolHandler),
                // por eso hay que usar el setter directo y no connector.setProperty(...)
                connector.setMaxLocaleCount(50));
    }
}