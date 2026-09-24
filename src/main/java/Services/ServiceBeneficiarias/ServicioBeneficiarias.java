package Services.ServiceBeneficiarias;

import Sdonaciones.dominio.donacion.BienAsignado;
import Sdonaciones.dominio.donacion.DonacionSegmentada;
import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import Sdonaciones.dominio.necesidad.Necesidad;
import Sdonaciones.repositorios.RepoEntidades;
import Sdonaciones.repositorios.RepoNecesidades;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioBeneficiarias {
    private final RepoEntidades repoBeneficiarias;
    private final RepoNecesidades repoNecesidades;


    public ServicioBeneficiarias(RepoEntidades repoBeneficiarias, RepoNecesidades repoNecesidades) {
        this.repoBeneficiarias = repoBeneficiarias;
        this.repoNecesidades = repoNecesidades;

    }

    public List<EntidadBeneficiaria> getAllEntidades() {
        return repoBeneficiarias.listarTodas();
    }


    public EntidadBeneficiaria getEntidadById(Long idEntidad) {
        return repoBeneficiarias.obtenerPorId(idEntidad);
    }

    public void seSatisfaceLaNecesidad(Necesidad necesidadResuelta) {
        EntidadBeneficiaria entidad = getEntidadById(necesidadResuelta.getEntidadBeneficiaria().getId());
        entidad.eliminarNecesidadActual(necesidadResuelta);
        repoNecesidades.eliminarNecesidad(necesidadResuelta.getId());
        repoBeneficiarias.actualizarEntidad(entidad);
    }

    public Necesidad buscarNecesidadDeEntidad(Long idNecesidad) {
        // flatMap = "a cada elemento, le extraé su lista interna, y junta todas las listas en una sola".
        // En este caso Stream<Stream<Necesidad>> a -> Stream<Necesidad>
        return getAllEntidades().stream()
                .flatMap(p -> p.getNecesidadesActuales().stream())
                .filter(n -> n.getId().equals(idNecesidad))
                .findFirst()
                .orElse(null);
    }


    public EntidadBeneficiaria createEntidad(EntidadBeneficiaria entidad) {
        repoBeneficiarias.guardar(entidad);
        for (Integer i = 0; i < entidad.getNecesidadesActuales().size(); i++) {
            Necesidad necesidad = entidad.getNecesidadesActuales().get(i);
            necesidad.setEntidadBeneficiaria(entidad);
            repoNecesidades.guardar(necesidad);
        }
        return entidad;

    }

    public EntidadBeneficiaria updateEntidad(Long idEntidad, EntidadBeneficiaria updatedEntidad) {
        EntidadBeneficiaria antigua = repoBeneficiarias.obtenerPorId(idEntidad);
        antigua.setNecesidadesActuales(updatedEntidad.getNecesidadesActuales());
        antigua.setNecesidadesHistoricas(updatedEntidad.getNecesidadesHistoricas());
        antigua.setDireccion(updatedEntidad.getDireccion());
        antigua.setTelefono(updatedEntidad.getTelefono());
        antigua.setRazonSocial(updatedEntidad.getRazonSocial());
        antigua.setCorreoRepresentante(updatedEntidad.getCorreoRepresentante());
        repoBeneficiarias.actualizarEntidad(updatedEntidad);
        return updatedEntidad;
    }

    public String deleteEntidad(Long idEntidad) {
        repoBeneficiarias.eliminarEntidad(idEntidad);
        repoNecesidades.eliminarNecesidadesDeEntidad(idEntidad);
        return "Entidad eliminada.";
    }


    public void asignarDonacionANecesidad(Necesidad necesidad, DonacionSegmentada donacion) {
        Integer cantidadPorEntregar = Math.min(donacion.getBien().getCantidadActual(), necesidad.getCantidadObjetivo());
        necesidad.recibirBienes(cantidadPorEntregar);
        donacion.restarBienesAsignados(cantidadPorEntregar);
        donacion.agregarCantidadDeBienDonado(new BienAsignado(cantidadPorEntregar, necesidad.getId()));
    }


}
