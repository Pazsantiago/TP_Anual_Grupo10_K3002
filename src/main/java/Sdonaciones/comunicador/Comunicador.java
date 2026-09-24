package Sdonaciones.comunicador;

import Sdonaciones.dominio.donacion.DonacionAsignada;
import Sdonaciones.dominio.donacion.DonacionAsignadaDTO;
import Sdonaciones.dominio.donacion.DonacionSegmentada;
import Sdonaciones.dominio.donacion.DonacionSegmentadaDTO;
import Sdonaciones.dominio.donante.Donante;
import Sdonaciones.dominio.donante.DonanteDTO;
import Sdonaciones.dominio.donante.TipoMedioContacto;
import Sdonaciones.dominio.mensajes.Destinatario;
import Sdonaciones.dominio.mensajes.MensajeNotificacion;
import Sdonaciones.dominio.mensajes.TipoEvento;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Component
public class Comunicador {
    @Async
    public void comunicarMensaje(String correo, TipoMedioContacto tipoTel, String telefono, TipoEvento tipoEvento, RestClient restClient) {
        Destinatario destinatario = new Destinatario();
        destinatario.setEmail(correo);
        if (tipoTel.equals(TipoMedioContacto.TELEFONO)) {
            destinatario.setTelefono(telefono);
            destinatario.setWhatsapp(null);
        } else if (tipoTel.equals(TipoMedioContacto.WHATSAPP)) {
            destinatario.setWhatsapp(telefono);
            destinatario.setTelefono(null);
        }
        restClient
                .post()
                .uri("/mensajes")
                .body(new MensajeNotificacion(tipoEvento, destinatario, null))
                .retrieve()
                .toBodilessEntity();
    }

    public DonanteDTO aDonanteDTO(Donante donante) {
        return new DonanteDTO(donante.getId(), donante.getDonaciones(), donante.obtenerContactoPredeterminado());
    }

    public DonacionSegmentadaDTO aDonacionSegmentadaDTO(DonacionSegmentada donacionSegmentada, Long idNecesidad) {
        return new DonacionSegmentadaDTO(donacionSegmentada.getId(), donacionSegmentada.getEstadoActual().getTipoEstado(),
                donacionSegmentada.getSubcategoria(), idNecesidad, donacionSegmentada.getCantidadBienAsignadoPorId(idNecesidad)
        );
    }

    public DonacionAsignadaDTO aDonacionAsignadaDTO(DonacionAsignada donacionAsignada) {
        return new DonacionAsignadaDTO(donacionAsignada.getId(), donacionAsignada.getNecesidadResuelta().getEntidadBeneficiaria().getDireccion(),
                donacionAsignada.getDonacionesSegmentadas().stream().map(s -> aDonacionSegmentadaDTO(s, donacionAsignada.getNecesidadResuelta().getId()))
                        .toList()
        );
    }

    public void enviarDonacionAsignada(DonacionAsignada donacionAsignada, String ruta, RestClient restclient) {
        Integer result = restclient
                .post()
                .uri(ruta)
                .body(aDonacionAsignadaDTO(donacionAsignada))
                .retrieve()
                .body(Integer.class);
        if (result != 200) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void enviarDonante(Donante donante, String ruta, RestClient restclient) {
        Integer result = restclient
                .post()
                .uri(ruta)
                .body(aDonanteDTO(donante))
                .retrieve()
                .body(Integer.class);
        if (result != 200) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void enviarDonanteActualizado(Donante donante, String ruta, RestClient restclient) {
        Integer result = restclient
                .put()
                .uri(ruta)
                .body(aDonanteDTO(donante))
                .retrieve()
                .body(Integer.class);
        if (result != 200) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void avisarDonanteEliminado(String ruta, RestClient restclient) {
        Integer result = restclient
                .delete()
                .uri(ruta)
                .retrieve()
                .body(Integer.class);
        if (result != 200) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
