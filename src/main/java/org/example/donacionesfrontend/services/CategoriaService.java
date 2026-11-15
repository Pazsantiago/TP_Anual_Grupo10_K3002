package org.example.donacionesfrontend.services;

import org.example.donacionesfrontend.dtos.response.CategoriaResponseDTO;
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

    public List<CategoriaResponseDTO> getAll() {
        return restClient.get()
                .uri("/api/categorias")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CategoriaResponseDTO>>() {});
    }
}
