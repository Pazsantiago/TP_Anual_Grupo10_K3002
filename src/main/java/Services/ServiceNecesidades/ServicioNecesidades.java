package Services.ServiceNecesidades;

import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import Sdonaciones.dominio.necesidad.Necesidad;
import Sdonaciones.repositorios.RepoEntidades;
import Sdonaciones.repositorios.RepoNecesidades;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

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


    public Necesidad getNecesidadById(Integer idNecesidad, Integer idEntidad) {
        return repoNecesidades.buscarPorId(idNecesidad, idEntidad);
    }


    public Necesidad createNecesidad(Integer idEntidad, Necesidad necesidad) {
        EntidadBeneficiaria entidad = repoEntidades.obtenerPorId(idEntidad);
        entidad.agregarNecesidadActual(necesidad);
        repoEntidades.actualizarEntidad(entidad.getId(), entidad);
        repoNecesidades.guardar(necesidad);
        return necesidad;
    }


    @PutMapping("/{idNecesidad}/entidad/{idEntidad}")
    public Necesidad updateNecesidad(Integer idNecesidad, Integer idEntidad, Necesidad updatedNecesidad) {
        EntidadBeneficiaria entidad = repoEntidades.obtenerPorId(idEntidad);
        entidad.agregarNecesidadActual(updatedNecesidad);
        repoEntidades.actualizarEntidad(entidad.getId(), entidad);
        repoNecesidades.actualizarNecesidad(idNecesidad, idEntidad, updatedNecesidad);
        return updatedNecesidad;
    }

    @DeleteMapping("/{idNecesidad}/entidad/{idEntidad}")
    public String deleteNecesidad(Integer idNecesidad, Integer idEntidad) {
        EntidadBeneficiaria entidad = repoEntidades.obtenerPorId(idEntidad);
        Necesidad necesidad = entidad.getNecesidadesActuales().stream().filter(n -> n.getId().equals(idNecesidad)).findFirst().orElse(null);
        entidad.eliminarNecesidadActual(necesidad);
        repoNecesidades.eliminarNecesidad(idNecesidad, idEntidad);
        return "Necesidad borrada";
    }
}
