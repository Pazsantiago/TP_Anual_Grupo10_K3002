package org.example.donacionesfrontend.services;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dtos.request.DonacionRequestDTO;
import org.example.donacionesfrontend.dtos.response.DonacionResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class DonacionService {

    private final RestClient restClient;

    public DonacionService(@Qualifier("restClientDonaciones") RestClient restClient) {
        this.restClient = restClient;
    }

    public List<DonacionResponseDTO> getByDonante(Long idDonante) {
        return restClient.get()
                .uri("/api/donantes/{idDonante}/donaciones", idDonante)
                .retrieve()
                .body(new ParameterizedTypeReference<List<DonacionResponseDTO>>() {});
    }

    public void create(Long idDonante, DonacionRequestDTO donacion) {
        restClient.post()
                .uri("/api/donantes/{idDonante}/donaciones", idDonante)
                .body(donacion)
                .retrieve()
                .toBodilessEntity();
    }

    public void delete(Long idDonante, Long idDonacion) {
        restClient.delete()
                .uri("/api/donantes/{idDonante}/donaciones/{idDonacion}", idDonante, idDonacion)
                .retrieve()
                .toBodilessEntity();
    }
}