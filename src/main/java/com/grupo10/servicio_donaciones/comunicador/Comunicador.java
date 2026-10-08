package com.grupo10.servicio_donaciones.comunicador;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.*;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante.Donante;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante.DonanteDTO;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante.TipoMedioContacto;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.mensajes.Destinatario;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.mensajes.MensajeNotificacion;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.mensajes.TipoEvento;
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
                .body(new MensajeNotificacion(tipoEvento, destinatario, "", ""))
                .retrieve()
                .toBodilessEntity();
    }

    public DonacionDTO aDonacionDTO(Donacion donacion) {
        return new DonacionDTO(donacion.getId(), donacion.getFechaRegistro(), donacion.conocerCategorias(), donacion.conocerCantidadDeBienesDonados(), donacion.conocerCantSegmentadasEntregadas());
    }

    public DonanteDTO aDonanteDTO(Donante donante) {
        return new DonanteDTO(donante.getId(), donante.getDonaciones().stream().map(this::aDonacionDTO).toList(), donante.obtenerContactoPredeterminado());
    }

    public DonacionSegmentadaDTO aDonacionSegmentadaDTO(DonacionSegmentada donacionSegmentada) {
        return new DonacionSegmentadaDTO(donacionSegmentada.getId(), donacionSegmentada.getDonacionInicial().getDonante().getId());
    }

    public DonacionAsignadaDTO aDonacionAsignadaDTO(DonacionAsignada donacionAsignada) {
        return new DonacionAsignadaDTO(donacionAsignada.getId(),
                donacionAsignada.getNecesidadResuelta().getEntidadBeneficiaria().getDireccion(),
                donacionAsignada.getDonacionesSegmentadas().stream().map(s -> aDonacionSegmentadaDTO(s))
                        .toList(),
                donacionAsignada.getNecesidadResuelta().getId()
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
