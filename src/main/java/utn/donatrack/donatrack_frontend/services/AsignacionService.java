package utn.donatrack.donatrack_frontend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class AsignacionService {
    @Qualifier("restClientDonaciones")
    private final RestClient restClient;

    public void asignarDonacionANecesidad(Long idEntidad, Long idNecesidad, Long idDonacionSegmentada) {
        restClient.post()
                .uri("/api/entidadesBeneficiarias/{idEntidad}/necesidades/{idNecesidad}/donaciones/{idDonacion}",
                        idEntidad, idNecesidad, idDonacionSegmentada)
                .retrieve()
                .toBodilessEntity();
    }

    public void agregarSegmentadaAAsignada(Long idDonacionAsignada, Long idDonacionSegmentada) {
        restClient.post()
                .uri("/api/donacionesAsignadas/{idAsignada}/segmentadas/{idSegmentada}",
                        idDonacionAsignada, idDonacionSegmentada)
                .retrieve()
                .toBodilessEntity();
    }

}