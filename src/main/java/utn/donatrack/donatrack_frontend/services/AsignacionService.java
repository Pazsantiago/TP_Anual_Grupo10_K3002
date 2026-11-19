package utn.donatrack.donatrack_frontend.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import utn.donatrack.donatrack_frontend.client.ClienteServicio;

@Service
public class AsignacionService extends ClienteServicio {

    public AsignacionService(@Qualifier("restClientDonaciones") RestClient restClient) {
        super(restClient, "donaciones");
    }

    public void asignarDonacionANecesidad(Long idEntidad, Long idNecesidad, Long idDonacionSegmentada) {
        ejecutar(() -> http().post()
                .uri("/api/entidadesBeneficiarias/{idEntidad}/necesidades/{idNecesidad}/donaciones/{idDonacion}",
                        idEntidad, idNecesidad, idDonacionSegmentada)
                .retrieve()
                .toBodilessEntity());
    }

    public void agregarSegmentadaAAsignada(Long idDonacionAsignada, Long idDonacionSegmentada) {
        ejecutar(() -> http().post()
                .uri("/api/donacionesAsignadas/{idAsignada}/segmentadas/{idSegmentada}",
                        idDonacionAsignada, idDonacionSegmentada)
                .retrieve()
                .toBodilessEntity());
    }
}