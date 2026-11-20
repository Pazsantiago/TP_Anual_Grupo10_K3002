package utn.donatrack.donatrack_frontend.services;

import lombok.RequiredArgsConstructor;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.NecesidadRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.NecesidadResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NecesidadService {
    @Qualifier("restClientDonaciones")
    private final RestClient restClient;

    public List<NecesidadResponseDTO> getByEntidad(Long idEntidad) {
        return restClient.get()
                .uri("/api/entidadesBeneficiarias/{idEntidad}/necesidades", idEntidad)
                .retrieve()
                .body(new ParameterizedTypeReference<List<NecesidadResponseDTO>>() {});
    }

    public void create(Long idEntidad, NecesidadRequestDTO necesidad) {
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