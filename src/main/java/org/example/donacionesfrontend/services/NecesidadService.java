package org.example.donacionesfrontend.services;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dtos.NecesidadDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NecesidadService {

    private final RestClient restClientDonaciones;

    public List<NecesidadDTO> getByEntidad(Long idEntidad) {
        return restClientDonaciones.get()
                .uri("/api/entidadesBeneficiarias/{idEntidad}/necesidades", idEntidad)
                .retrieve()
                .body(new ParameterizedTypeReference<List<NecesidadDTO>>() {});
    }

    public NecesidadDTO create(Long idEntidad, NecesidadDTO dto) {
        return restClientDonaciones.post()
                .uri("/api/entidadesBeneficiarias/{idEntidad}/necesidades", idEntidad)
                .body(dto)
                .retrieve()
                .body(NecesidadDTO.class);
    }

    public void delete(Long idEntidad, Long idNecesidad) {
        restClientDonaciones.delete()
                .uri("/api/entidadesBeneficiarias/{idEntidad}/necesidades/{idNecesidad}", idEntidad, idNecesidad)
                .retrieve()
                .toBodilessEntity();
    }
}