package Services.ServiceDonaciones;

import Sdonaciones.dominio.donacion.Donacion;
import Sdonaciones.dominio.donacion.DonacionAsignada;
import Sdonaciones.dominio.donacion.DonacionSegmentada;
import Sdonaciones.repositorios.RepoDonaciones;
import Sdonaciones.repositorios.RepoDonacionesAsignadas;
import Sdonaciones.repositorios.RepoDonantes;
import Services.ServiceAsignacion.ServicioAsignacion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioDonacion {
    private RepoDonaciones repoDonaciones;
    private RepoDonacionesAsignadas repoDonacionesAsignadas;
    private ServicioAsignacion servicioAsignacion;
    private RepoDonantes repoDonantes;

    public ServicioDonacion(ServicioAsignacion servicioAsignacion, RepoDonacionesAsignadas repoDonacionesAsignadas, RepoDonaciones repoDonaciones, RepoDonantes repoDonantes) {
        this.servicioAsignacion = servicioAsignacion;
        this.repoDonacionesAsignadas = repoDonacionesAsignadas;
        this.repoDonaciones = repoDonaciones;
        this.repoDonantes = repoDonantes;
    }


    public List<Donacion> getAllDonaciones() {
        return repoDonaciones.getDonaciones();
    }


    public Donacion getDonacionById(Integer idDonacion) {
        return repoDonaciones.getDonaciones().stream()
                .filter(d -> d.getId() == idDonacion)
                .findFirst()
                .orElse(null);
    }


    public List<DonacionSegmentada> obtenerDonacionesSegmentadas() {
        List<DonacionSegmentada> lista = new ArrayList<>();
        repoDonaciones.getDonaciones().forEach(d -> {
            lista.addAll(d.getDonacionesSegmentadas());
        });
        return lista;
    }

    public DonacionSegmentada obtenerDonacionSegmentada(Integer idSegmentada) {
        return repoDonaciones.getDonaciones().stream().filter(d ->
                        d.getDonacionesSegmentadas().stream().anyMatch(s -> s.getId() == idSegmentada))
                .findFirst().get().getDonacionesSegmentadas().stream().filter(e ->
                        e.getId() == idSegmentada
                ).findFirst().get();
    }


    public List<DonacionAsignada> obtenerDonacionesAsignadas() {
        return repoDonacionesAsignadas.listarTodas();
    }


    public Donacion createDonacion(Donacion donacion, Integer idDonante) {
        donacion.setDonante(repoDonantes.buscarPorId(idDonante));
        repoDonaciones.guardar(donacion);
        servicioAsignacion.agregarDonacionesSegmentadas(donacion.getDonacionesSegmentadas());
        return donacion;

    }

    public DonacionAsignada asignarDonacionAEntidadPorNecesidad(Integer idDonacion, Integer idEntidad, Integer idNecesidad) {
        return servicioAsignacion.asignarDonacion(idDonacion, idEntidad, idNecesidad);
    }


    public Donacion updateDonacion(Donacion updateDonacion, Integer idDonacion) {
        repoDonaciones.actualizarDonacion(idDonacion, updateDonacion);
        return updateDonacion;
    }


    public String deleteDonacion(Integer idDonacion) {
        repoDonaciones.borrarDonacion(idDonacion);
        return "Donacion borrada";
    }
}
