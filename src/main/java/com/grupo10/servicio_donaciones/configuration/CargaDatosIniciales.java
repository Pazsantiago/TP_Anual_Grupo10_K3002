package com.grupo10.servicio_donaciones.configuration;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.categoria.Categoria;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.categoria.TipoCategoria;
import com.grupo10.servicio_donaciones.repositorios.RepoCategorias;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CargaDatosIniciales {


    @Bean
    CommandLineRunner cargarCategorias(RepoCategorias repoCategorias) {
        return args -> {
            if (repoCategorias.count() == 0) {
                repoCategorias.save(
                        new Categoria("Comida", TipoCategoria.ALIMENTICIO)
                );

                repoCategorias.save(
                        new Categoria("Ropa", TipoCategoria.VESTIMENTA)
                );

                repoCategorias.save(
                        new Categoria("Muebles", TipoCategoria.MOBILIARIO)
                );

                repoCategorias.save(
                        new Categoria("Perecederos", TipoCategoria.PERECEDERO)
                );
            }
        };
    }
}