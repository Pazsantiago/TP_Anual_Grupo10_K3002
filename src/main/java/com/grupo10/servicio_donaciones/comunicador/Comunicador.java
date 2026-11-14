package com.grupo10.servicio_donaciones.comunicador;

import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.Donacion;
import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.DonacionAsignada;
import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.DonacionSegmentada;
import com.grupo10.servicio_donaciones.donaciones.dominio.donante.*;
import com.grupo10.servicio_donaciones.donaciones.mensajes.DestinatarioRequestDTO;
import com.grupo10.servicio_donaciones.donaciones.mensajes.NotificacionRequestDTO;
import com.grupo10.servicio_donaciones.donaciones.mensajes.TipoEvento;
import com.grupo10.servicio_donaciones.dtos.integracion.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Component
public class Comunicador {
    public void comunicarMensaje(String nombre, String correo, String telefono, MedioNotificacion tipoNotificacion, TipoEvento tipoEvento, RestClient restClient) {
        restClient
                .post()
                .uri("/mensajes")
                .body(new NotificacionRequestDTO(tipoNotificacion, "Prueba", "Notificacion de evento", new DestinatarioRequestDTO(nombre, telefono, correo, telefono), tipoEvento.toString()))
                .retrieve()
                .toBodilessEntity();
    }


    public DonacionDTO aDonacionDTO(Donacion donacion) {
        return new DonacionDTO(donacion.getId(), donacion.getFechaRegistro(), donacion.conocerCategorias(), donacion.conocerCantidadDeBienesDonados(), donacion.getDonacionesSegmentadas().stream().map(DonacionSegmentada::getId).toList());
    }

    public MedioContactoDTO aMedioContactoDTO(MedioContacto medioContacto) {
        return new MedioContactoDTO(medioContacto.getTipo(), medioContacto.getCorreoElectronico(), medioContacto.getTelefono());
    }

    public DonanteDTO aDonanteDTO(Donante donante) {
        String nombre = (donante.getPersona() instanceof PersonaHumana ph)
                ? ph.getNombre()
                : ((PersonaJuridica) donante.getPersona()).getRazonSocial();
        return new DonanteDTO(donante.getId(), nombre, donante.getDonaciones().stream().map(this::aDonacionDTO).toList(), aMedioContactoDTO(donante.obtenerContactoPredeterminado()));
    }

    public DonacionSegmentadaDTO aDonacionSegmentadaDTO(DonacionSegmentada donacionSegmentada) {
        return new DonacionSegmentadaDTO(donacionSegmentada.getId(),
                donacionSegmentada.getBien().getCantidadActual(),
                donacionSegmentada.getBien().getUnidad());
    }

    public DonacionAsignadaDTO aDonacionAsignadaDTO(DonacionAsignada donacionAsignada) {
        return new DonacionAsignadaDTO(donacionAsignada.getId(),
                donacionAsignada.getDonacionesSegmentadas().stream().map(this::aDonacionSegmentadaDTO).toList(),
                donacionAsignada.getNecesidadResuelta().getEntidadBeneficiaria().getId(),
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
