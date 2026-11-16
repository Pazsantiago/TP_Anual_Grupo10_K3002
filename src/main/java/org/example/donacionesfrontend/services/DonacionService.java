package org.example.donacionesfrontend.services;

import lombok.RequiredArgsConstructor;
import org.example.donacionesfrontend.dtos.request.DonacionRequestDTO;
import org.example.donacionesfrontend.dtos.request.EstadoDonacionRequestDTO;
import org.example.donacionesfrontend.dtos.response.DonacionAsignadaResponseDTO;
import org.example.donacionesfrontend.dtos.response.DonacionResponseDTO;
import org.example.donacionesfrontend.dtos.response.DonacionSegmentadaResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonacionService {
    @Qualifier("restClientDonaciones")
    private final RestClient restClient;

    public List<DonacionResponseDTO> getByDonante(Long idDonante) {
        return restClient.get()
                .uri("/api/donantes/{idDonante}/donaciones", idDonante)
                .retrieve()
                .body(new ParameterizedTypeReference<List<DonacionResponseDTO>>() {});
    }

    public List<DonacionSegmentadaResponseDTO> getSegmentadasByDonante(Long idDonante) {
        return restClient.get()
                .uri("/api/donantes/{idDonante}/donaciones/segmentadas", idDonante)
                .retrieve()
                .body(new ParameterizedTypeReference<List<DonacionSegmentadaResponseDTO>>() {});
    }

    public List<DonacionAsignadaResponseDTO> getAsignadasPorEntidad(Long idDonante) {
        return restClient.get()
                .uri("/api/entidadesBeneficiarias/{idEntidad}/donaciones-asignadas", idDonante)
                .retrieve()
                .body(new ParameterizedTypeReference<List<DonacionAsignadaResponseDTO>>() {});
    }

    public void actualizarEstadoSegmentada(Long idDonante, Long idDonacion, Long idSegmentada, EstadoDonacionRequestDTO estadoDTO) {
        restClient.get()
                .uri("/api/donantes/{idDonante}/donaciones/{idDonacion}/segmentadas/{idSegmentada}", idDonante, idDonacion, idSegmentada)
                .retrieve()
                .toBodilessEntity();
    }

    public void create(Long idDonante, DonacionRequestDTO donacion) {
        restClient.post()
                .uri("/api/donantes/{idDonante}/donaciones", idDonante)
                .body(donacion)
                .retrieve()
                .toBodilessEntity();
    }

    public void delete(Long idDonante, Long idDonacion) {
        restClient.delete()
                .uri("/api/donantes/{idDonante}/donaciones/{idDonacion}", idDonante, idDonacion)
                .retrieve()
                .toBodilessEntity();
    }
}