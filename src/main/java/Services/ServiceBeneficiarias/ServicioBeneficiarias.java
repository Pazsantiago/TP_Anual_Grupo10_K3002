package Services.ServiceBeneficiarias;

import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
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


    public EntidadBeneficiaria getEntidadById(Integer idEntidad) {
        return repoBeneficiarias.obtenerPorId(idEntidad);
    }


    public EntidadBeneficiaria createEntidad(EntidadBeneficiaria entidad) {
        repoBeneficiarias.guardar(entidad);
        for (Integer i = 0; i < entidad.getNecesidadesActuales().size(); i++) {
            entidad.getNecesidadesActuales().get(i).setEntidadBeneficiaria(entidad);
            repoNecesidades.guardar(entidad.getNecesidadesActuales().get(i));
        }
        return entidad;

    }

    public EntidadBeneficiaria updateEntidad(Integer idEntidad, EntidadBeneficiaria updatedEntidad) {
        updatedEntidad.getNecesidadesActuales().forEach(n ->
                repoNecesidades.actualizarNecesidad(n.getId(), updatedEntidad.getId(), n)
        );
        repoBeneficiarias.actualizarEntidad(idEntidad, updatedEntidad);
        return updatedEntidad;
    }


    public String deleteEntidad(Integer idEntidad) {
        repoBeneficiarias.obtenerPorId(idEntidad).getNecesidadesActuales().forEach(n ->
                repoNecesidades.eliminarNecesidad(n.getId(), idEntidad)
        );
        repoBeneficiarias.eliminarEntidad(idEntidad);
        return "Entidad eliminada.";
    }
}
