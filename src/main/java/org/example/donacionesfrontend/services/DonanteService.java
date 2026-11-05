package org.example.donacionesfrontend.services;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dtos.DonanteDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonanteService {

    private final RestClient restClientDonaciones;

    public List<DonanteDTO> getAll() {
        return restClientDonaciones.get()
                .uri("/api/donantes")
                .retrieve()
                .body(new ParameterizedTypeReference<List<DonanteDTO>>() {});
    }

    public DonanteDTO getById(Long id) {
        return restClientDonaciones.get()
                .uri("/api/donantes/{id}", id)
                .retrieve()
                .body(DonanteDTO.class);
    }

    public DonanteDTO create(DonanteDTO dto) {
        return restClientDonaciones.post()
                .uri("/api/donantes")
                .body(dto)
                .retrieve()
                .body(DonanteDTO.class);
    }

    public DonanteDTO update(Long id, DonanteDTO dto) {
        return restClientDonaciones.put()
                .uri("/api/donantes/{id}", id)
                .body(dto)
                .retrieve()
                .body(DonanteDTO.class);
    }

    public void delete(Long id) {
        restClientDonaciones.delete()
                .uri("/api/donantes/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}