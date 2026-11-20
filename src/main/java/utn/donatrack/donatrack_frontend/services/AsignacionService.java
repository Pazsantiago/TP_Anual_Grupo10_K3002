package utn.donatrack.donatrack_frontend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class AsignacionService {

    private final RestClient donacionesRestClient;

    public void asignarDonacionANecesidad(Long idEntidad, Long idNecesidad, Long idDonacionSegmentada) {
        donacionesRestClient.post()
                .uri("/api/entidadesBeneficiarias/{idEntidad}/necesidades/{idNecesidad}/donaciones/{idDonacion}",
                        idEntidad, idNecesidad, idDonacionSegmentada)
                .retrieve()
                .toBodilessEntity();
    }

    public void agregarSegmentadaAAsignada(Long idDonacionAsignada, Long idDonacionSegmentada) {
        donacionesRestClient.post()
                .uri("/api/donacionesAsignadas/{idAsignada}/segmentadas/{idSegmentada}",
                        idDonacionAsignada, idDonacionSegmentada)
                .retrieve()
                .toBodilessEntity();
    }
}