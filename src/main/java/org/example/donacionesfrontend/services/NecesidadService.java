package org.example.donacionesfrontend.services;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dominio.necesidad.Necesidad;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NecesidadService {

    private final RestClient restClient;

    public List<Necesidad> getByEntidad(Long idEntidad) {
        return restClient.get()
                .uri("/api/entidadesBeneficiarias/{idEntidad}/necesidades", idEntidad)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Necesidad>>() {});
    }

    public void create(Long idEntidad, Necesidad necesidad) {
        restClient.post()
                .uri("/api/entidadesBeneficiarias/{idEntidad}/necesidades", idEntidad)
                .body(necesidad)
                .retrieve()
                .toBodilessEntity();
    }

    public void delete(Long idEntidad, Long idNecesidad) {
        restClient.delete()
                .uri("/api/entidadesBeneficiarias/{idEntidad}/necesidades/{idNecesidad}", idEntidad, idNecesidad)
                .retrieve()
                .toBodilessEntity();
    }
}