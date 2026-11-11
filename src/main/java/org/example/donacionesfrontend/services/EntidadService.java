package org.example.donacionesfrontend.services;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dominio.entidad.EntidadBeneficiaria;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class EntidadService {

    private final RestClient restClient;

    public EntidadService(@Qualifier("restClientDonaciones") RestClient restClient) {
        this.restClient = restClient;
    }
    public List<EntidadBeneficiaria> getAll() {
        return restClient.get()
                .uri("/api/entidadesBeneficiarias")
                .retrieve()
                .body(new ParameterizedTypeReference<List<EntidadBeneficiaria>>() {});
    }

    public EntidadBeneficiaria getById(Long id) {
        return restClient.get()
                .uri("/api/entidadesBeneficiarias/{id}", id)
                .retrieve()
                .body(EntidadBeneficiaria.class);
    }

    public void create(EntidadBeneficiaria entidad) {
        restClient.post()
                .uri("/api/entidadesBeneficiarias")
                .body(entidad)
                .retrieve()
                .toBodilessEntity();
    }

    public void delete(Long id) {
        restClient.delete()
                .uri("/api/entidadesBeneficiarias/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}