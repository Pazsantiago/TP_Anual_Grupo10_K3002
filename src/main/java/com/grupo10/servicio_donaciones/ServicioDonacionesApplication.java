package com.grupo10.servicio_donaciones;

import Sdonaciones.dominio.donacion.Donacion;
import Sdonaciones.repositorios.RepositorioDonaciones;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"Sdonaciones",
		"Cdonaciones"
})
public class ServicioDonacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioDonacionesApplication.class, args);
	}

}
