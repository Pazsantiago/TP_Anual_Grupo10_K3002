package Services.ServiceDonaciones;

import Sdonaciones.comunicador.Comunicador;
import Sdonaciones.dominio.donacion.Donacion;
import Sdonaciones.dominio.donacion.DonacionAsignada;
import Sdonaciones.dominio.donacion.DonacionSegmentada;
import Sdonaciones.dominio.donacion.TipoEstadoDonacion;
import Sdonaciones.dominio.donante.Donante;
import Sdonaciones.repositorios.RepoDonaciones;
import Sdonaciones.repositorios.RepoDonacionesAsignadas;
import Sdonaciones.repositorios.RepoDonantes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioDonacion {
    private final RepoDonaciones repoDonaciones;
    private final RepoDonacionesAsignadas repoDonacionesAsignadas;
    private final RepoDonantes repoDonantes;
    private final RestClient restClientIncentivos;
    private final Comunicador comunicador;

    public ServicioDonacion(RepoDonacionesAsignadas repoDonacionesAsignadas, RepoDonaciones repoDonaciones,
                            RepoDonantes repoDonantes, @Qualifier("restClientIncentivos") RestClient restClientIncentivos, Comunicador comunicador) {
        this.repoDonaciones = repoDonaciones;
        this.repoDonantes = repoDonantes;
        this.repoDonacionesAsignadas = repoDonacionesAsignadas;
        this.restClientIncentivos = restClientIncentivos;
        this.comunicador = comunicador;
    }

    public List<Donacion> getAllDonaciones() {
        return repoDonaciones.getDonaciones();
    }


    public Donacion getDonacionById(Long idDonacion) {
        return repoDonaciones.getDonaciones().stream()
                .filter(d -> d.getId().equals(idDonacion))
                .findFirst()
                .orElse(null);
    }

    public Donacion buscarDonacionPorSegmentada(Long idSegmentada) {
        return getAllDonaciones().stream().filter(donacion -> donacion.getDonacionesSegmentadas().stream().anyMatch(s -> s.getId().equals(idSegmentada))).findFirst().orElse(null);
    }


    public List<DonacionSegmentada> obtenerDonacionesSegmentadas() {
        List<DonacionSegmentada> lista = new ArrayList<>();
        repoDonaciones.getDonaciones().forEach(d -> {
            lista.addAll(d.getDonacionesSegmentadas());
        });
        return lista;
    }

    public List<DonacionSegmentada> obtenerDonacionesSegmentadasEnDeposito() {
        return repoDonaciones.getDonaciones().stream().flatMap(d -> d.getDonacionesSegmentadas().stream())
                .filter(s -> s.getEstadoActual().getTipoEstado().equals(TipoEstadoDonacion.EN_DEPOSITO))
                .toList();
    }


    public DonacionSegmentada obtenerDonacionSegmentada(Long idSegmentada) {
        return repoDonaciones.getDonaciones().stream().filter(d ->
                        d.getDonacionesSegmentadas().stream().anyMatch(s -> s.getId() == idSegmentada))
                .findFirst().get().getDonacionesSegmentadas().stream().filter(e ->
                        e.getId() == idSegmentada
                ).findFirst().get();
    }


    public List<DonacionAsignada> obtenerDonacionesAsignadas() {
        return repoDonacionesAsignadas.listarTodas();
    }


    public Donacion createDonacion(Donacion donacion, Long idDonante) {
        donacion.setDonante(repoDonantes.buscarPorId(idDonante));
        repoDonantes.buscarPorId(idDonante).agregarDonacion(donacion);
        donacion.segmentarse(repoDonaciones.getUltimoIdDonacionSegmentada());
        repoDonaciones.guardar(donacion);
        //comunicador.enviarDonante(repoDonantes.buscarPorId(idDonante), "/donantes", restClientIncentivos);
        return donacion;

    }


    public Donacion updateDonacion(Long idDonacion, Donacion updateDonacion) {
        Donacion oldDonacion = repoDonaciones.buscarPorId(idDonacion);
        oldDonacion.setDonacionesSegmentadas(updateDonacion.getDonacionesSegmentadas());
        oldDonacion.setDonante(updateDonacion.getDonante());
        oldDonacion.setDescripcionGeneral(updateDonacion.getDescripcionGeneral());
        oldDonacion.setFechaRegistro(updateDonacion.getFechaRegistro());
        oldDonacion.setBienesDeEntrada(updateDonacion.getBienesDeEntrada());
        repoDonaciones.actualizarDonacion(updateDonacion);
        Donante donante = repoDonantes.buscarPorId(oldDonacion.getDonante().getId());
        repoDonantes.actualizarDonante(donante);
        //comunicador.enviarDonanteActualizado(donante, "/donantes/{id}" + donante.getId(), restClientIncentivos);
        return updateDonacion;
    }


    public String deleteDonacion(Long idDonacion) {
        Donante donante = repoDonaciones.buscarPorId(idDonacion).getDonante();
        donante.eliminarDonacion(idDonacion);
        repoDonaciones.borrarDonacion(idDonacion);
        //comunicador.enviarDonanteActualizado(donante, "/donantes/{id}" + donante.getId(), restClientIncentivos);
        return "Donacion borrada";
    }


}
