package utn.donatrack.donatrack_frontend.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import utn.donatrack.donatrack_frontend.client.ClienteServicio;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.EntidadBeneficiariaRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.EntidadBeneficiariaResponseDTO;

import java.util.List;

@Service
public class EntidadService extends ClienteServicio {

    public EntidadService(@Qualifier("restClientDonaciones") RestClient restClient) {
        super(restClient, "donaciones");
    }

    public List<EntidadBeneficiariaResponseDTO> getAll() {
        return listaDe(() -> http().get()
                .uri("/api/entidadesBeneficiarias")
                .retrieve()
                .body(new ParameterizedTypeReference<List<EntidadBeneficiariaResponseDTO>>() {}));
    }

    public EntidadBeneficiariaResponseDTO getById(Long id) {
        return call(() -> http().get()
                .uri("/api/entidadesBeneficiarias/{id}", id)
                .retrieve()
                .body(EntidadBeneficiariaResponseDTO.class));
    }

    public void create(EntidadBeneficiariaRequestDTO entidad) {
        ejecutar(() -> http().post()
                .uri("/api/entidadesBeneficiarias")
                .contentType(MediaType.APPLICATION_JSON)
                .body(entidad)
                .retrieve()
                .toBodilessEntity());
    }

    public void delete(Long id) {
        ejecutar(() -> http().delete()
                .uri("/api/entidadesBeneficiarias/{id}", id)
                .retrieve()
                .toBodilessEntity());
    }
}