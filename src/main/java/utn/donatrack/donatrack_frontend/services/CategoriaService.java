package utn.donatrack.donatrack_frontend.services;

import lombok.RequiredArgsConstructor;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.CategoriaResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    @Qualifier("restClientDonaciones")
    private final RestClient restClient;

    public List<CategoriaResponseDTO> getAll() {
        return restClient.get()
                .uri("/api/categorias")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CategoriaResponseDTO>>() {});
    }
}
