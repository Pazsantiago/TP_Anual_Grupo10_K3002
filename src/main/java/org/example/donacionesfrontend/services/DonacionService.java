package org.example.donacionesfrontend.services;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dominio.donacion.Donacion;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonacionService {

    private final RestClient restClient;

    public List<Donacion> getByDonante(Long idDonante) {
        return restClient.get()
                .uri("/donantes/{idDonante}/donaciones", idDonante)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Donacion>>() {});
    }

    public void create(Long idDonante, Donacion donacion) {
        restClient.post()
                .uri("/donantes/{idDonante}/donaciones", idDonante)
                .body(donacion)
                .retrieve()
                .toBodilessEntity();
    }

    public void delete(Long idDonante, Long idDonacion) {
        restClient.delete()
                .uri("/donante/{idDonante}/donaciones/{idDonacion}", idDonante, idDonacion)
                .retrieve()
                .toBodilessEntity();
    }
}