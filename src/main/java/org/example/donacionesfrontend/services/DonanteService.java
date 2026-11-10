package org.example.donacionesfrontend.services;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dominio.donante.Donante;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonanteService {

    private final RestClient restClient;

    public List<Donante> getAll() {
        return restClient.get()
                .uri("/api/donantes")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Donante>>() {});
    }

    public Donante getById(Long id) {
        return restClient.get()
                .uri("/api/donantes/{id}", id)
                .retrieve()
                .body(Donante.class);
    }

    public void create(Donante donante) {
        restClient.post()
                .uri("/api/donantes")
                .body(donante)
                .retrieve()
                .toBodilessEntity();
    }

    public void delete(Long id) {
        restClient.delete()
                .uri("/api/donantes/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

    public void importarMasivoCsv(String rutaArchivo) {
        restClient.post()
                .uri(uriBuilder -> uriBuilder
                .path("/api/donantes/importador")
                .queryParam("rutaArchivo", rutaArchivo)
                .build())
            .retrieve()
                .toBodilessEntity();
    }

}