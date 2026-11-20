package utn.donatrack.donatrack_frontend.services;

import lombok.RequiredArgsConstructor;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.EntidadBeneficiariaRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.EntidadBeneficiariaResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntidadService {
    @Qualifier("restClientDonaciones")
    private final RestClient restClient;

    public List<EntidadBeneficiariaResponseDTO> getAll() {
        return restClient.get()
                .uri("/api/entidadesBeneficiarias")
                .retrieve()
                .body(new ParameterizedTypeReference<List<EntidadBeneficiariaResponseDTO>>() {});
    }

    public EntidadBeneficiariaResponseDTO getById(Long id) {
        return restClient.get()
                .uri("/api/entidadesBeneficiarias/{id}", id)
                .retrieve()
                .body(EntidadBeneficiariaResponseDTO.class);
    }

    public void create(EntidadBeneficiariaRequestDTO entidad) {
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