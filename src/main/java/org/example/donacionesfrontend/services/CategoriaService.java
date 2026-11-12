package org.example.donacionesfrontend.services;

import org.example.donacionesfrontend.dominio.categoria.Categoria;
import org.example.donacionesfrontend.dominio.donante.Donante;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class CategoriaService {
    private final RestClient restClient;

    public CategoriaService(@Qualifier("restClientDonaciones") RestClient restClient) {
        this.restClient = restClient;
    }

    public List<Categoria> getAll() {
        return restClient.get()
                .uri("/api/categorias")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Categoria>>() {});
    }
}
