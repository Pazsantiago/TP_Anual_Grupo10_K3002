package org.example.coladenotificaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = {
        "configuration",
        "Controllers",
        "domain",
        "Repository",
        "Services"
})
public class ColaDeNotificacionesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ColaDeNotificacionesApplication.class, args);
    }

}
