package utn.donatrack.donatrack_frontend.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import utn.donatrack.donatrack_frontend.client.ClienteServicio;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.DonanteRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.DonanteResponseDTO;

import java.util.List;

@Service
public class DonanteService extends ClienteServicio {

    public DonanteService(@Qualifier("restClientDonaciones") RestClient restClient) {
        super(restClient, "donaciones");
    }

    public List<DonanteResponseDTO> getAll() {
        return listaDe(() -> http().get()
                .uri("/api/donantes")
                .retrieve()
                .body(new ParameterizedTypeReference<List<DonanteResponseDTO>>() {}));
    }

    public DonanteResponseDTO getById(Long id) {
        return call(() -> http().get()
                .uri("/api/donantes/{id}", id)
                .retrieve()
                .body(DonanteResponseDTO.class));
    }

    public void create(DonanteRequestDTO donante) {
        ejecutar(() -> http().post()
                .uri("/api/donantes")
                .contentType(MediaType.APPLICATION_JSON)
                .body(donante)
                .retrieve()
                .toBodilessEntity());
    }

    public void createByAdmin(DonanteRequestDTO donante) {
        ejecutar(() -> http().post()
                .uri("/api/donantes/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .body(donante)
                .retrieve()
                .toBodilessEntity());
    }

    public void delete(Long id) {
        ejecutar(() -> http().delete()
                .uri("/api/donantes/{id}", id)
                .retrieve()
                .toBodilessEntity());
    }

    public void importarMasivoCsv(MultiValueMap<String, Object> body) {
        ejecutar(() -> http().post()
                .uri("/api/donantes/importador")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .toBodilessEntity());
    }
}