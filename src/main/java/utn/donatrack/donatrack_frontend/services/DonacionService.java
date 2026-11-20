package utn.donatrack.donatrack_frontend.services;

import lombok.RequiredArgsConstructor;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.DonacionRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.request.EstadoDonacionRequestDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.DonacionAsignadaResponseDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.DonacionResponseDTO;
import utn.donatrack.donatrack_frontend.client.dto.donaciones.response.DonacionSegmentadaResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonacionService {

    private final RestClient donacionesRestClient;

    public List<DonacionResponseDTO> getByDonante(Long idDonante) {
        return donacionesRestClient.get()
                .uri("/api/donantes/{idDonante}/donaciones", idDonante)
                .retrieve()
                .body(new ParameterizedTypeReference<List<DonacionResponseDTO>>() {});
    }

    public List<DonacionSegmentadaResponseDTO> getSegmentadasByDonante(Long idDonante) {
        return donacionesRestClient.get()
                .uri("/api/donantes/{idDonante}/donaciones/segmentadas", idDonante)
                .retrieve()
                .body(new ParameterizedTypeReference<List<DonacionSegmentadaResponseDTO>>() {});
    }

    public List<DonacionAsignadaResponseDTO> getAsignadasPorEntidad(Long idDonante) {
        return donacionesRestClient.get()
                .uri("/api/entidadesBeneficiarias/{idEntidad}/donaciones-asignadas", idDonante)
                .retrieve()
                .body(new ParameterizedTypeReference<List<DonacionAsignadaResponseDTO>>() {});
    }

    public void actualizarEstadoSegmentada(Long idDonante, Long idDonacion, Long idSegmentada, EstadoDonacionRequestDTO estadoDTO) {
        donacionesRestClient.put()
                .uri("/api/donantes/{idDonante}/donaciones/{idDonacion}/segmentadas/{idSegmentada}", idDonante, idDonacion, idSegmentada)
                .body(estadoDTO)
                .retrieve()
                .toBodilessEntity();
    }

    public void create(Long idDonante, DonacionRequestDTO donacion) {
        donacionesRestClient.post()
                .uri("/api/donantes/{idDonante}/donaciones", idDonante)
                .body(donacion)
                .retrieve()
                .toBodilessEntity();
    }

    public void delete(Long idDonante, Long idDonacion) {
        donacionesRestClient.delete()
                .uri("/api/donantes/{idDonante}/donaciones/{idDonacion}", idDonante, idDonacion)
                .retrieve()
                .toBodilessEntity();
    }
}