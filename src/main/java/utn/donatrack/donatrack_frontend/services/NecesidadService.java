package utn.donatrack.donatrack_frontend.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import utn.donatrack.donatrack_frontend.client.ClienteServicio;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.NecesidadRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.NecesidadResponseDTO;

import java.util.List;

@Service
public class NecesidadService extends ClienteServicio {

    public NecesidadService(@Qualifier("restClientDonaciones") RestClient restClient) {
        super(restClient, "donaciones");
    }

    public List<NecesidadResponseDTO> getByEntidad(Long idEntidad) {
        return listaDe(() -> http().get()
                .uri("/api/entidadesBeneficiarias/{idEntidad}/necesidades", idEntidad)
                .retrieve()
                .body(new ParameterizedTypeReference<List<NecesidadResponseDTO>>() {}));
    }

    public void create(Long idEntidad, NecesidadRequestDTO necesidad) {
        ejecutar(() -> http().post()
                .uri("/api/entidadesBeneficiarias/{idEntidad}/necesidades", idEntidad)
                .contentType(MediaType.APPLICATION_JSON)
                .body(necesidad)
                .retrieve()
                .toBodilessEntity());
    }

    public void delete(Long idEntidad, Long idNecesidad) {
        ejecutar(() -> http().delete()
                .uri("/api/entidadesBeneficiarias/{idEntidad}/necesidades/{idNecesidad}", idEntidad, idNecesidad)
                .retrieve()
                .toBodilessEntity());
    }
}