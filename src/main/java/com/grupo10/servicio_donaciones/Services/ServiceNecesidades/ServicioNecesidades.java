package com.grupo10.servicio_donaciones.Services.ServiceNecesidades;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.necesidad.Necesidad;
import com.grupo10.servicio_donaciones.Services.ServiceBeneficiarias.ServicioBeneficiarias;
import com.grupo10.servicio_donaciones.repositorios.RepoEntidades;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServicioNecesidades {
    private final RepoEntidades repoEntidades;
    private final ServicioBeneficiarias servicioBeneficiarias;


    public List<Necesidad> getAllNecesidades() {
        return repoEntidades.findNecesidades();
    }


    public Necesidad getNecesidadById(Long idNecesidad) {
        return repoEntidades.findNecesidadById(idNecesidad).orElse(null);
    }

    @Transactional
    public Necesidad createNecesidad(Long idEntidad, Necesidad necesidad) {
        EntidadBeneficiaria entidad = servicioBeneficiarias.getEntidadById(idEntidad);
        entidad.agregarNecesidadActual(necesidad);
        repoEntidades.save(entidad);
        return necesidad;
    }

    @Transactional
    public Necesidad updateNecesidad(Long idNecesidad, Necesidad updatedNecesidad) {
        Necesidad necesidad = servicioBeneficiarias.buscarNecesidadDeEntidad(idNecesidad);
        EntidadBeneficiaria entidadBeneficiaria = servicioBeneficiarias.getEntidadById(necesidad.getEntidadBeneficiaria().getId());
        necesidad.aplicarActualizacion(updatedNecesidad);
        entidadBeneficiaria.actualizarNecesidad(necesidad);
        repoEntidades.save(entidadBeneficiaria);
        return necesidad;
    }

    @Transactional
    public String deleteNecesidad(Long idNecesidad) {
        Necesidad necesidad = this.servicioBeneficiarias.buscarNecesidadDeEntidad(idNecesidad);
        EntidadBeneficiaria entidad = necesidad.getEntidadBeneficiaria();
        entidad.eliminarNecesidadActual(necesidad.getId());
        servicioBeneficiarias.updateEntidad(entidad.getId(), entidad);
        return "Necesidad borrada";
    }
}
