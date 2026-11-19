package utn.donatrack.donatrack_frontend.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import utn.donatrack.donatrack_frontend.client.ClienteServicio;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.CategoriaResponseDTO;

import java.util.List;

@Service
public class CategoriaService extends ClienteServicio {

    public CategoriaService(@Qualifier("restClientDonaciones") RestClient restClient) {
        super(restClient, "donaciones");
    }

    public List<CategoriaResponseDTO> getAll() {
        return listaDe(() -> http().get()
                .uri("/api/categorias")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CategoriaResponseDTO>>() {}));
    }
}