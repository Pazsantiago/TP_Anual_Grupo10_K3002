package Services.ServiceNecesidades;

import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import Sdonaciones.dominio.necesidad.Necesidad;
import Sdonaciones.repositorios.RepoEntidades;
import Sdonaciones.repositorios.RepoNecesidades;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioNecesidades {
    private RepoNecesidades repoNecesidades;
    private RepoEntidades repoEntidades;

    public ServicioNecesidades(RepoNecesidades repoNecesidades, RepoEntidades repoEntidades) {
        this.repoNecesidades = repoNecesidades;
        this.repoEntidades = repoEntidades;

    }

    public List<Necesidad> getAllNecesidades() {
        return repoNecesidades.listarTodas();
    }


    public Necesidad getNecesidadById(Integer idNecesidad) {
        return repoNecesidades.buscarPorId(idNecesidad);
    }


    public Necesidad createNecesidad(Integer idEntidad, Necesidad necesidad) {
        EntidadBeneficiaria entidad = repoEntidades.obtenerPorId(idEntidad);
        entidad.agregarNecesidadActual(necesidad);
        repoEntidades.actualizarEntidad(entidad.getId(), entidad);
        repoNecesidades.guardar(necesidad);
        return necesidad;
    }

    // FIJARSE ACA DEVUELTA POR SI ACASO
    public Necesidad updateNecesidad(Integer idNecesidad, Necesidad updatedNecesidad) {
        EntidadBeneficiaria entidad = repoNecesidades.buscarPorId(idNecesidad).getEntidadBeneficiaria();
        entidad.agregarNecesidadActual(updatedNecesidad);
        repoEntidades.actualizarEntidad(entidad.getId(), entidad);
        repoNecesidades.actualizarNecesidad(idNecesidad, entidad.getId(), updatedNecesidad);
        return updatedNecesidad;
    }

    public String deleteNecesidad(Integer idNecesidad) {
        Necesidad necesidad = repoNecesidades.buscarPorId(idNecesidad);
        EntidadBeneficiaria entidad = necesidad.getEntidadBeneficiaria();
        entidad.eliminarNecesidadActual(necesidad);
        repoNecesidades.eliminarNecesidad(idNecesidad);
        return "Necesidad borrada";
    }
}
