package org.example.donacionesfrontend.services;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dominio.entidad.EntidadBeneficiaria;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntidadService {

    private final RestClient restClient;

    public List<EntidadBeneficiaria> getAll() {
        return restClient.get()
                .uri("/entidadesBeneficiarias")
                .retrieve()
                .body(new ParameterizedTypeReference<List<EntidadBeneficiaria>>() {});
    }

    public EntidadBeneficiaria getById(Long id) {
        return restClient.get()
                .uri("/entidadesBeneficiarias/{id}", id)
                .retrieve()
                .body(EntidadBeneficiaria.class);
    }

    public void create(EntidadBeneficiaria entidad) {
        restClient.post()
                .uri("/entidadesBeneficiarias")
                .body(entidad)
                .retrieve()
                .toBodilessEntity();
    }

    public void delete(Long id) {
        restClient.delete()
                .uri("/entidadesBeneficiarias/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}