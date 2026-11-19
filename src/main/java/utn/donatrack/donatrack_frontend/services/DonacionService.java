package utn.donatrack.donatrack_frontend.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import utn.donatrack.donatrack_frontend.client.ClienteServicio;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.DonacionRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.EstadoDonacionRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.DonacionAsignadaResponseDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.DonacionResponseDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.DonacionSegmentadaResponseDTO;

import java.util.List;

@Service
public class DonacionService extends ClienteServicio {

    public DonacionService(@Qualifier("restClientDonaciones") RestClient restClient) {
        super(restClient, "donaciones");
    }

    public List<DonacionResponseDTO> getByDonante(Long idDonante) {
        return listaDe(() -> http().get()
                .uri("/api/donantes/{idDonante}/donaciones", idDonante)
                .retrieve()
                .body(new ParameterizedTypeReference<List<DonacionResponseDTO>>() {}));
    }

    public List<DonacionSegmentadaResponseDTO> getSegmentadasByDonante(Long idDonante) {
        return listaDe(() -> http().get()
                .uri("/api/donantes/{idDonante}/donaciones/segmentadas", idDonante)
                .retrieve()
                .body(new ParameterizedTypeReference<List<DonacionSegmentadaResponseDTO>>() {}));
    }

    public List<DonacionAsignadaResponseDTO> getAsignadasPorEntidad(Long idEntidad) {
        return listaDe(() -> http().get()
                .uri("/api/entidadesBeneficiarias/{idEntidad}/donaciones-asignadas", idEntidad)
                .retrieve()
                .body(new ParameterizedTypeReference<List<DonacionAsignadaResponseDTO>>() {}));
    }

    public void actualizarEstadoSegmentada(Long idDonante, Long idDonacion, Long idSegmentada, EstadoDonacionRequestDTO estadoDTO) {
        ejecutar(() -> http().patch()
                .uri("/api/donantes/{idDonante}/donaciones/{idDonacion}/segmentadas/{idSegmentada}", idDonante, idDonacion, idSegmentada)
                .contentType(MediaType.APPLICATION_JSON)
                .body(estadoDTO)
                .retrieve()
                .toBodilessEntity());
    }

    public void create(Long idDonante, DonacionRequestDTO donacion) {
        ejecutar(() -> http().post()
                .uri("/api/donantes/{idDonante}/donaciones", idDonante)
                .contentType(MediaType.APPLICATION_JSON)
                .body(donacion)
                .retrieve()
                .toBodilessEntity());
    }

    public void delete(Long idDonante, Long idDonacion) {
        ejecutar(() -> http().delete()
                .uri("/api/donantes/{idDonante}/donaciones/{idDonacion}", idDonante, idDonacion)
                .retrieve()
                .toBodilessEntity());
    }
}