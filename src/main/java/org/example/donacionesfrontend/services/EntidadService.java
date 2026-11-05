package org.example.donacionesfrontend.services;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dtos.EntidadBeneficiariaDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntidadService {

    private final RestClient restClientDonaciones;

    public List<EntidadBeneficiariaDTO> getAll() {
        return restClientDonaciones.get()
                .uri("/api/entidadesBeneficiarias")
                .retrieve()
                .body(new ParameterizedTypeReference<List<EntidadBeneficiariaDTO>>() {});
    }

    public EntidadBeneficiariaDTO getById(Long id) {
        return restClientDonaciones.get()
                .uri("/api/entidadesBeneficiarias/{id}", id)
                .retrieve()
                .body(EntidadBeneficiariaDTO.class);
    }

    public EntidadBeneficiariaDTO create(EntidadBeneficiariaDTO dto) {
        return restClientDonaciones.post()
                .uri("/api/entidadesBeneficiarias")
                .body(dto)
                .retrieve()
                .body(EntidadBeneficiariaDTO.class);
    }

    public EntidadBeneficiariaDTO update(Long id, EntidadBeneficiariaDTO dto) {
        return restClientDonaciones.put()
                .uri("/api/entidadesBeneficiarias/{id}", id)
                .body(dto)
                .retrieve()
                .body(EntidadBeneficiariaDTO.class);
    }

    public void delete(Long id) {
        restClientDonaciones.delete()
                .uri("/api/entidadesBeneficiarias/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}