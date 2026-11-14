package org.example.donacionesfrontend.services;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dtos.request.DonanteRequestDTO;
import org.example.donacionesfrontend.dtos.response.DonanteResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class DonanteService {

    private final RestClient restClient;

    public DonanteService(@Qualifier("restClientDonaciones") RestClient restClient) {
        this.restClient = restClient;
    }

    public List<DonanteResponseDTO> getAll() {
        return restClient.get()
                .uri("/api/donantes")
                .retrieve()
                .body(new ParameterizedTypeReference<List<DonanteResponseDTO>>() {});
    }

    public DonanteResponseDTO getById(Long id) {
        return restClient.get()
                .uri("/api/donantes/{id}", id)
                .retrieve()
                .body(DonanteResponseDTO.class);
    }

    public void create(DonanteRequestDTO donante) {
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