package Services.ServiceDonacionAsignada;

import Sdonaciones.comunicador.Comunicador;
import Sdonaciones.dominio.donacion.*;
import Sdonaciones.dominio.donante.Donante;
import Sdonaciones.dominio.donante.TipoMedioContacto;
import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import Sdonaciones.dominio.mensajes.MensajeConfirmacionEntrega;
import Sdonaciones.dominio.mensajes.MensajeEntregaFallida;
import Sdonaciones.dominio.mensajes.MensajeInicioDeRuta;
import Sdonaciones.dominio.mensajes.TipoEvento;
import Sdonaciones.dominio.necesidad.Necesidad;
import Sdonaciones.repositorios.RepoDonacionesAsignadas;
import Services.ServiceBeneficiarias.ServicioBeneficiarias;
import Services.ServiceDonaciones.ServicioDonacion;
import Services.ServiceDonantes.ServicioDonantes;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Date;
import java.util.List;

@Service
@Data
public class ServicioDonacionAsignada {
    private ServicioDonantes servicioDonantes;
    private ServicioDonacion servicioDonacion;
    private ServicioBeneficiarias servicioBeneficiarias;
    private final RepoDonacionesAsignadas repoDonacionesAsignadas;
    private final RestClient restClientMensajes;
    private final RestClient restClientBrokerLogistica;
    private final Comunicador comunicador;

    public ServicioDonacionAsignada(ServicioDonantes servicioDonantes,
                                    ServicioDonacion servicioDonacion, ServicioBeneficiarias servicioBeneficiarias, RepoDonacionesAsignadas repoDonacionesAsignadas,
                                    @Qualifier("restClientMensajes") RestClient restClientMensajesConfig,
                                    @Qualifier("restClientBrokerLogistica") RestClient restClientBrokerLogistica,
                                    Comunicador comunicador) {
        this.servicioDonantes = servicioDonantes;
        this.servicioDonacion = servicioDonacion;
        this.servicioBeneficiarias = servicioBeneficiarias;
        this.repoDonacionesAsignadas = repoDonacionesAsignadas;
        this.restClientMensajes = restClientMensajesConfig;
        this.restClientBrokerLogistica = restClientBrokerLogistica;
        this.comunicador = comunicador;
    }

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

    public void comenzarRuta(MensajeInicioDeRuta mensajeInicioDeRuta) {
        DonacionAsignada donacionAsignada = repoDonacionesAsignadas.buscarPorIdNecesidad(mensajeInicioDeRuta.getIdNecesidad());
        donacionAsignada.getDonacionesSegmentadas().forEach(s -> {
            s.cambiarEstadoActual(new EstadoDonacion(TipoEstadoDonacion.EN_TRASLADO, null));
            servicioDonacion.updateDonacion(s.getDonacionInicial().getId(), s.getDonacionInicial());
        });
        //notificarMensajes(mensajeInicioDeRuta.getIdDonantesIncluidos(), mensajeInicioDeRuta.getIdEntidadBeneficiaria(), TipoEvento.INICIO_DE_RUTA);
    }

    public void entregaConfirmada(MensajeConfirmacionEntrega mensajeConfirmacionEntrega) {
        DonacionAsignada donacionAsignada = repoDonacionesAsignadas.buscarPorIdNecesidad(mensajeConfirmacionEntrega.getIdNecesidad());
        donacionAsignada.setAsignacionCompleta(new AsignacionCompleta(
                mensajeConfirmacionEntrega.getNroComprobante(), mensajeConfirmacionEntrega.getIdCamionAsignado(), mensajeConfirmacionEntrega.getFecha())
        );
        donacionAsignada.getDonacionesSegmentadas().forEach(s -> {
            s.cambiarEstadoActual(new EstadoDonacion(TipoEstadoDonacion.EN_TRASLADO, null));
            servicioDonacion.updateDonacion(s.getDonacionInicial().getId(), s.getDonacionInicial());
        });
        servicioBeneficiarias.seSatisfaceLaNecesidad(donacionAsignada.getNecesidadResuelta());
        repoDonacionesAsignadas.actualizarDonacion(donacionAsignada.getId(), donacionAsignada);
        //notificarMensajes(mensajeConfirmacionEntrega.getIdDonantesIncluidos(), mensajeConfirmacionEntrega.getIdEntidadBeneficiaria(), TipoEvento.ENTREGA_REALIZADA);
    }

    public void entregaFallida(MensajeEntregaFallida mensajeEntregaFallida) {
        DonacionAsignada donacionAsignada = repoDonacionesAsignadas.buscarPorIdNecesidad(mensajeEntregaFallida.getIdNecesidad());
        donacionAsignada.getDonacionesSegmentadas().forEach(s -> {
            s.cambiarEstadoActual(new EstadoDonacion(TipoEstadoDonacion.ENTREGA_FALLIDA, mensajeEntregaFallida.getJustificacion()));
        });
        if (!mensajeEntregaFallida.getSePuedeReplanificar()) {
            reasignarBienesADonaciones(donacionAsignada);
            servicioBeneficiarias.updateEntidad(donacionAsignada.getNecesidadResuelta().getEntidadBeneficiaria().getId(), donacionAsignada.getNecesidadResuelta().getEntidadBeneficiaria());
            repoDonacionesAsignadas.eliminar(donacionAsignada.getId());
        }
        //notificarMensajes(mensajeEntregaFallida.getIdDonantesIncluidos(), mensajeEntregaFallida.getIdEntidadBeneficiaria(), TipoEvento.ENTREGA_REALIZADA);
    }

    public void reasignarBienesADonaciones(DonacionAsignada donacionAsignada) {
        Necesidad necesidad = donacionAsignada.getNecesidadResuelta();
        donacionAsignada.getDonacionesSegmentadas().forEach(donacionSegmentada -> {
            Integer cantidadBienesAsignadaANecesidad = donacionSegmentada.getCantidadBienAsignadoPorId(donacionAsignada.getNecesidadResuelta().getId());
            necesidad.quitarBienes(cantidadBienesAsignadaANecesidad);
            donacionSegmentada.sumarBienesAsignados(cantidadBienesAsignadaANecesidad);
            donacionSegmentada.cambiarEstadoActual(new EstadoDonacion(TipoEstadoDonacion.EN_DEPOSITO, null));
            servicioDonacion.updateDonacion(donacionSegmentada.getDonacionInicial().getId(), donacionSegmentada.getDonacionInicial());
        });
    }


    public String agregarDonacionADonacionAsignadaIncompleta(Long idDonacionAsignada, Long idDonacionSegmentada) {
        DonacionAsignada donacionAsignada = repoDonacionesAsignadas.buscarPorId(idDonacionAsignada);
        DonacionSegmentada donacionSegmentada = servicioDonacion.obtenerDonacionSegmentada(idDonacionSegmentada);
        donacionAsignada.agregarDonacionSegmentada(donacionSegmentada);
        servicioBeneficiarias.asignarDonacionANecesidad(donacionAsignada.getNecesidadResuelta(), donacionSegmentada);
        return "Ok";
    }

    public DonacionAsignada asignarDonacionAEntidadConNecesidad(Long idDonacionSegmentada, Long idEntidad, Long idNecesidad) {
        // Se utiliza el repo de donaciones ya que son estas las que cambian
        // y NO el donante especificamente, las donaciones se actualizan por medio de su repo con sus metodos encapsulados
        DonacionSegmentada donacionSegmentada = servicioDonacion.obtenerDonacionSegmentada(idDonacionSegmentada);
        EntidadBeneficiaria entidad = servicioBeneficiarias.getEntidadById(idEntidad);
        Necesidad necesidad = servicioBeneficiarias.buscarNecesidadDeEntidad(idNecesidad);

        servicioBeneficiarias.asignarDonacionANecesidad(necesidad, donacionSegmentada);
        servicioBeneficiarias.updateEntidad(entidad.getId(), entidad);

        Donacion donacionOriginal = servicioDonacion.buscarDonacionPorSegmentada(idDonacionSegmentada);
        donacionSegmentada.cambiarEstadoActual(new EstadoDonacion(TipoEstadoDonacion.ASIGNACION_REALIZADA, null));
        servicioDonacion.updateDonacion(donacionOriginal.getId(), donacionOriginal);

        DonacionAsignada donacionFinal = new DonacionAsignada(repoDonacionesAsignadas.obtenerUltimoID(), List.of(donacionSegmentada), necesidad, new Date(), null);
        repoDonacionesAsignadas.guardar(donacionFinal);
        repoDonacionesAsignadas.actualizarDonacion(idDonacionSegmentada, donacionFinal);

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
