package Services.ServiceDonaciones;

import Sdonaciones.asignacion.ServicioAsignacion;
import Sdonaciones.dominio.donacion.Donacion;
import Sdonaciones.dominio.donacion.DonacionAsignada;
import Sdonaciones.dominio.donacion.DonacionSegmentada;
import Sdonaciones.repositorios.RepoDonaciones;
import Sdonaciones.repositorios.RepoDonacionesAsignadas;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioDonacion {
    private RepoDonaciones repoDonaciones;
    private RepoDonacionesAsignadas repoDonacionesAsignadas;
    private ServicioAsignacion servicioAsignacion;


    public ServicioDonacion(ServicioAsignacion servicioAsignacion, RepoDonacionesAsignadas repoDonacionesAsignadas, RepoDonaciones repoDonaciones) {
        this.servicioAsignacion = servicioAsignacion;
        this.repoDonacionesAsignadas = repoDonacionesAsignadas;
        this.repoDonaciones = repoDonaciones;
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


    public Donacion createDonacion(Donacion donacion) {
        repoDonaciones.guardar(donacion);
        servicioAsignacion.agregarDonacionesSegmentadas(donacion.getDonacionesSegmentadas());
        servicioAsignacion.generarRanking();
        return donacion;

    }

    public DonacionAsignada asignarDonacionAEntidadPorNecesidad(Integer idDonacion, Integer idEntidad, Integer idNecesidad) {
        return servicioAsignacion.asignarDonacion(idDonacion, idEntidad, idNecesidad);
    }


    public Donacion updateDonacion(Donacion updateDonacion, Integer idDonacion) {
        repoDonaciones.actualizarDonacion(idDonacion, updateDonacion);
        return updateDonacion;
    }


    public String deleteDonacion(@PathVariable Integer idDonacion) {
        repoDonaciones.borrarDonacion(idDonacion);
        return "Donacion borrada";
    }
}
