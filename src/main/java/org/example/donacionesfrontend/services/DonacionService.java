package org.example.donacionesfrontend.services;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dtos.DonacionDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonacionService {

    private final RestClient restClientDonaciones;

    public List<DonacionDTO> getByDonante(Long idDonante) {
        return restClientDonaciones.get()
                .uri("/api/donantes/{idDonante}/donaciones", idDonante)
                .retrieve()
                .body(new ParameterizedTypeReference<List<DonacionDTO>>() {});
    }

    public DonacionDTO create(Long idDonante, DonacionDTO dto) {
        return restClientDonaciones.post()
                .uri("/api/donantes/{idDonante}/donaciones", idDonante)
                .body(dto)
                .retrieve()
                .body(DonacionDTO.class);
    }

    public void delete(Long idDonante, Long idDonacion) {
        restClientDonaciones.delete()
                .uri("/api/donantes/{idDonante}/donaciones/{idDonacion}", idDonante, idDonacion)
                .retrieve()
                .toBodilessEntity();
    }
}