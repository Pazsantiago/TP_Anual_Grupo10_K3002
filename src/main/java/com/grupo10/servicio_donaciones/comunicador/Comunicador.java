package com.grupo10.servicio_donaciones.comunicador;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.*;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante.Donante;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante.DonanteDTO;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante.MedioNotificacion;
import com.grupo10.servicio_donaciones.Sdonaciones.mensajes.DestinatarioRequestDTO;
import com.grupo10.servicio_donaciones.Sdonaciones.mensajes.NotificacionRequestDTO;
import com.grupo10.servicio_donaciones.Sdonaciones.mensajes.TipoEvento;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Component
public class Comunicador {
    @Async
    public void comunicarMensaje(String nombre, String correo, String telefono, MedioNotificacion tipoNotificacion, TipoEvento tipoEvento, RestClient restClient) {
        restClient
                .post()
                .uri("/mensajes")
                .body(new NotificacionRequestDTO(tipoNotificacion, "Prueba", "Notificacion de evento", new DestinatarioRequestDTO(nombre, telefono, correo, telefono), tipoEvento.toString()))
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
                donacionAsignada.getNecesidadResuelta().getEntidadBeneficiaria().getId(),
                donacionAsignada.getDonacionesSegmentadas().stream().map(s -> s.getDonacionInicial().getDonante().getId()).toList(),
                donacionAsignada.getNecesidadResuelta().getEntidadBeneficiaria().getDireccion()
        );
    }

    public void enviarDonacionAsignada(DonacionAsignada donacionAsignada, RestClient restclient) {
        ResponseEntity<Void> result = restclient
                .post()
                .uri("/api/donacionesAsignadas")
                .body(aDonacionAsignadaDTO(donacionAsignada))
                .retrieve()
                .toBodilessEntity();
        if (result.getStatusCode().value() != 200) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public void enviarDonante(Donante donante, RestClient restclient) {
        Integer result = restclient
                .post()
                .uri("/perfiles")
                .body(aDonanteDTO(donante))
                .retrieve()
                .body(Integer.class);
        if (result != 200) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void enviarDonanteActualizado(Donante donante, RestClient restclient) {
        Integer result = restclient
                .put()
                .uri("/perfiles/{idDonante}" + donante.getId())
                .body(aDonanteDTO(donante))
                .retrieve()
                .body(Integer.class);
        if (result != 200) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void avisarDonanteEliminado(Long id, RestClient restclient) {
        Integer result = restclient
                .delete()
                .uri("/perfiles/{idDonante}" + id)
                .retrieve()
                .body(Integer.class);
        if (result != 200) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
