package com.grupo10.servicio_donaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class ServicioDonacionesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicioDonacionesApplication.class, args);

    }

}

