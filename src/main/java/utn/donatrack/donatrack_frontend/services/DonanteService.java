package utn.donatrack.donatrack_frontend.services;

import lombok.RequiredArgsConstructor;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.DonanteRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.DonanteResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonanteService {

    private final RestClient donacionesRestClient;

    public List<DonanteResponseDTO> getAll() {
        return donacionesRestClient.get()
                .uri("/api/donantes")
                .retrieve()
                .body(new ParameterizedTypeReference<List<DonanteResponseDTO>>() {});
    }

    public DonanteResponseDTO getByEmail(String email) {
        return donacionesRestClient.get()
                .uri("/api/donantes/email?email={email}", email)
                .retrieve()
                .body(DonanteResponseDTO.class);
    }

    public DonanteResponseDTO getById(Long id) {
        return donacionesRestClient.get()
                .uri("/api/donantes/{id}", id)
                .retrieve()
                .body(DonanteResponseDTO.class);
    }

    public void create(DonanteRequestDTO donante) {
        donacionesRestClient.post()
                .uri("/api/donantes")
                .body(donante)
                .retrieve()
                .toBodilessEntity();
    }

    public void createByAdmin(DonanteRequestDTO donante, String email) {
        donacionesRestClient.post()
                .uri("/api/donantes/admin?email={email}", email)
                .body(donante)
                .retrieve()
                .toBodilessEntity();
    }

    public void delete(Long id) {
        donacionesRestClient.delete()
                .uri("/api/donantes/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

    public void importarMasivoCsv(MultiValueMap<String, Object> body) {
        donacionesRestClient.post()
                .uri("/importador")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .toBodilessEntity();
    }
}