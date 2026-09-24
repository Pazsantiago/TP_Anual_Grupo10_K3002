package Services.ServiceNecesidades;

import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import Sdonaciones.dominio.necesidad.Necesidad;
import Sdonaciones.repositorios.RepoNecesidades;
import Services.ServiceBeneficiarias.ServicioBeneficiarias;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioNecesidades {
    private RepoNecesidades repoNecesidades;
    private ServicioBeneficiarias servicioBeneficiarias;

    public ServicioNecesidades(RepoNecesidades repoNecesidades, ServicioBeneficiarias servicioBeneficiarias) {
        this.repoNecesidades = repoNecesidades;
        this.servicioBeneficiarias = servicioBeneficiarias;

    }

    public List<Necesidad> getAllNecesidades() {
        return repoNecesidades.listarTodas();
    }


    public Necesidad getNecesidadById(Long idNecesidad) {
        return repoNecesidades.buscarPorId(idNecesidad);
    }


    public Necesidad createNecesidad(Long idEntidad, Necesidad necesidad) {
        EntidadBeneficiaria entidad = servicioBeneficiarias.getEntidadById(idEntidad);
        necesidad.setEntidadBeneficiaria(entidad);
        entidad.agregarNecesidadActual(necesidad);
        servicioBeneficiarias.updateEntidad(entidad.getId(), entidad);
        repoNecesidades.guardar(necesidad);
        return necesidad;
    }

    public Necesidad updateNecesidad(Long idNecesidad, Necesidad updatedNecesidad) {
        Necesidad necesidad = servicioBeneficiarias.buscarNecesidadDeEntidad(idNecesidad);
        necesidad.aplicarActualizacion(updatedNecesidad);
        EntidadBeneficiaria entidadBeneficiaria = servicioBeneficiarias.getEntidadById(necesidad.getEntidadBeneficiaria().getId());
        servicioBeneficiarias.updateEntidad(entidadBeneficiaria.getId(), entidadBeneficiaria);
        return updatedNecesidad;
    }

    public String deleteNecesidad(Long idNecesidad) {
        Necesidad necesidad = repoNecesidades.buscarPorId(idNecesidad);
        EntidadBeneficiaria entidad = necesidad.getEntidadBeneficiaria();
        entidad.eliminarNecesidadActual(necesidad);
        repoNecesidades.eliminarNecesidad(idNecesidad);
        return "Necesidad borrada";
    }
}
