package com.grupo10.servicio_donaciones.Services.ServiceDonacionAsignada;

import com.grupo10.servicio_donaciones.Sdonaciones.comunicador.Comunicador;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.*;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante.Donante;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante.TipoMedioContacto;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.mensajes.MensajeConfirmacionEntrega;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.mensajes.MensajeEntregaFallida;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.mensajes.MensajeInicioDeRuta;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.mensajes.TipoEvento;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.necesidad.Necesidad;
import com.grupo10.servicio_donaciones.Services.ServiceBeneficiarias.ServicioBeneficiarias;
import com.grupo10.servicio_donaciones.Services.ServiceDonaciones.ServicioDonacion;
import com.grupo10.servicio_donaciones.Services.ServiceDonantes.ServicioDonantes;
import com.grupo10.servicio_donaciones.repositorios.RepoDonacionesAsignadas;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Data
@Transactional(readOnly = true)
public class ServicioDonacionAsignada {
    private final ServicioDonantes servicioDonantes;
    private final ServicioDonacion servicioDonacion;
    private final ServicioBeneficiarias servicioBeneficiarias;
    private final RepoDonacionesAsignadas repoDonacionesAsignadas;
    @Qualifier("restClientMensajes")
    private final RestClient restClientMensajes;
    @Qualifier("restClientBrokerLogistica")
    private final RestClient restClientBrokerLogistica;
    private final Comunicador comunicador;


    public void notificarMensajes(List<Long> donantesID, Long idEntidad, TipoEvento tipoEvento) {
        donantesID.forEach(id -> {
            Donante donante = servicioDonantes.obtenerPorId(id);
            comunicador.comunicarMensaje(donante.obtenerContactoPredeterminado().getCorreoElectronico(),
                    donante.obtenerContactoPredeterminado().getTipo(),
                    donante.obtenerContactoPredeterminado().getTelefono(),
                    tipoEvento, restClientMensajes);
        });

        EntidadBeneficiaria entidad = servicioBeneficiarias.getEntidadById(idEntidad);
        comunicador.comunicarMensaje(entidad.getCorreoRepresentante(),
                TipoMedioContacto.TELEFONO,
                entidad.getTelefono(),
                tipoEvento, restClientMensajes);
    }

    @Transactional
    public void comenzarRuta(MensajeInicioDeRuta mensajeInicioDeRuta) {
        DonacionAsignada donacionAsignada = repoDonacionesAsignadas.findByNecesidadResueltaId(mensajeInicioDeRuta.getIdNecesidad()).orElse(null);
        donacionAsignada.getDonacionesSegmentadas().forEach(s -> {
            s.cambiarEstadoActual(new EstadoDonacion(TipoEstadoDonacion.EN_TRASLADO, null, s));
            servicioDonacion.updateDonacion(s.getDonacionInicial().getId(), s.getDonacionInicial());
        });
        //notificarMensajes(mensajeInicioDeRuta.getIdDonantesIncluidos(), mensajeInicioDeRuta.getIdEntidadBeneficiaria(), TipoEvento.INICIO_DE_RUTA);
    }

    @Transactional
    public void entregaConfirmada(MensajeConfirmacionEntrega mensajeConfirmacionEntrega) {
        DonacionAsignada donacionAsignada = repoDonacionesAsignadas.findByNecesidadResueltaId(mensajeConfirmacionEntrega.getIdNecesidad()).orElse(null);
        donacionAsignada.setAsignacionCompleta(new AsignacionCompleta(mensajeConfirmacionEntrega.getNroComprobante(), mensajeConfirmacionEntrega.getIdCamionAsignado())
        );
        donacionAsignada.getDonacionesSegmentadas().forEach(s -> {
            s.cambiarEstadoActual(new EstadoDonacion(TipoEstadoDonacion.ENTREGADA, null, s));
            servicioDonacion.updateDonacion(s.getDonacionInicial().getId(), s.getDonacionInicial());
        });
        servicioBeneficiarias.seSatisfaceLaNecesidad(donacionAsignada.getNecesidadResuelta());
        repoDonacionesAsignadas.save(donacionAsignada);
        //notificarMensajes(mensajeConfirmacionEntrega.getIdDonantesIncluidos(), mensajeConfirmacionEntrega.getIdEntidadBeneficiaria(), TipoEvento.ENTREGA_REALIZADA);
    }

    @Transactional
    public void entregaFallida(MensajeEntregaFallida mensajeEntregaFallida) {
        DonacionAsignada donacionAsignada = repoDonacionesAsignadas.findByNecesidadResueltaId(mensajeEntregaFallida.getIdNecesidad()).orElse(null);
        donacionAsignada.getDonacionesSegmentadas().forEach(s -> {
            s.cambiarEstadoActual(new EstadoDonacion(TipoEstadoDonacion.ENTREGA_FALLIDA, mensajeEntregaFallida.getJustificacion(), s));
        });
        if (!mensajeEntregaFallida.getSePuedeReplanificar()) {
            reasignarBienesADonaciones(donacionAsignada);
            //servicioBeneficiarias.updateEntidad(donacionAsignada.getNecesidadResuelta().getEntidadBeneficiaria().getId(), donacionAsignada.getNecesidadResuelta().getEntidadBeneficiaria());
            repoDonacionesAsignadas.deleteById(donacionAsignada.getId());
        }
        //notificarMensajes(mensajeEntregaFallida.getIdDonantesIncluidos(), mensajeEntregaFallida.getIdEntidadBeneficiaria(), TipoEvento.ENTREGA_REALIZADA);
    }

    public void reasignarBienesADonaciones(DonacionAsignada donacionAsignada) {
        Necesidad necesidad = donacionAsignada.getNecesidadResuelta();
        donacionAsignada.getDonacionesSegmentadas().forEach(donacionSegmentada -> {
            Integer cantidadBienesAsignadaANecesidad = donacionSegmentada.getCantidadBienAsignadoPorId(donacionAsignada.getNecesidadResuelta().getId());
            necesidad.quitarBienes(cantidadBienesAsignadaANecesidad);
            donacionSegmentada.sumarBienesAsignados(cantidadBienesAsignadaANecesidad);
            donacionSegmentada.cambiarEstadoActual(new EstadoDonacion(TipoEstadoDonacion.EN_DEPOSITO, null, donacionSegmentada));
            donacionSegmentada.eliminarBienesAsignados(donacionAsignada.getNecesidadResuelta().getId());
            //servicioDonacion.updateDonacion(donacionSegmentada.getDonacionInicial().getId(), donacionSegmentada.getDonacionInicial());
        });
    }

    @Transactional
    public DonacionAsignada agregarDonacionADonacionAsignadaIncompleta(Long idDonacionAsignada, Long idDonacionSegmentada) {
        DonacionAsignada donacionAsignada = repoDonacionesAsignadas.findById(idDonacionAsignada).orElse(null);
        DonacionSegmentada donacionSegmentada = servicioDonacion.obtenerDonacionSegmentada(idDonacionSegmentada);
        donacionAsignada.agregarDonacionSegmentada(donacionSegmentada);
        servicioBeneficiarias.asignarDonacionANecesidad(donacionAsignada.getNecesidadResuelta(), donacionSegmentada);
        return donacionAsignada;
    }

    @Transactional
    public DonacionAsignada asignarDonacionAEntidadConNecesidad(Long idDonacionSegmentada, Long idEntidad, Long idNecesidad) {
        // Se utiliza el repo de donaciones ya que son estas las que cambian
        // y NO el donante especificamente, las donaciones se actualizan por medio de su repo con sus metodos encapsulados
        DonacionSegmentada donacionSegmentada = servicioDonacion.obtenerDonacionSegmentada(idDonacionSegmentada);
        EntidadBeneficiaria entidad = servicioBeneficiarias.getEntidadById(idEntidad);
        Necesidad necesidad = servicioBeneficiarias.buscarNecesidadDeEntidad(idNecesidad);

        servicioBeneficiarias.asignarDonacionANecesidad(necesidad, donacionSegmentada);
        servicioBeneficiarias.updateEntidad(entidad.getId(), entidad);

        Donacion donacionOriginal = servicioDonacion.buscarDonacionPorIdSegmentada(idDonacionSegmentada);
        donacionSegmentada.cambiarEstadoActual(new EstadoDonacion(TipoEstadoDonacion.ASIGNACION_REALIZADA, null, donacionSegmentada));
        servicioDonacion.updateDonacion(donacionOriginal.getId(), donacionOriginal);

        DonacionAsignada donacionFinal = new DonacionAsignada();
        donacionFinal.setDonacionesSegmentadas(List.of(donacionSegmentada));
        donacionFinal.setNecesidadResuelta(necesidad);
        donacionFinal.setFechaHora(new Date());
        repoDonacionesAsignadas.save(donacionFinal);

        Donante donante = donacionSegmentada.getDonacionInicial().getDonante();

//        comunicador.comunicarMensaje(donante.obtenerContactoPredeterminado().getCorreoElectronico(),
//                donante.obtenerContactoPredeterminado().getTipo(),
//                donante.obtenerContactoPredeterminado().getTelefono(),
//                TipoEvento.DONACION_ASIGNADA_DONANTE, restClientMensajes);
//
//        comunicador.comunicarMensaje(entidad.getCorreoRepresentante(),
//                TipoMedioContacto.TELEFONO,
//                entidad.getTelefono(),
//                TipoEvento.DONACION_ASIGNADA_ENTIDAD, restClientMensajes);
//
//        comunicador.enviarDonacionAsignada(donacionFinal, "/donacionesAsignadas", restClientBrokerLogistica);
        return donacionFinal;
    }

}
