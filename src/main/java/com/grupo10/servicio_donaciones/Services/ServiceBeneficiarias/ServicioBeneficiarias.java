package com.grupo10.servicio_donaciones.Services.ServiceBeneficiarias;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.categoria.Categoria;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.BienAsignado;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.DonacionSegmentada;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.necesidad.Necesidad;
import com.grupo10.servicio_donaciones.repositorios.RepoCategorias;
import com.grupo10.servicio_donaciones.repositorios.RepoEntidades;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServicioBeneficiarias {
    private final RepoEntidades repoBeneficiarias;
    private final RepoCategorias repoCategorias;

    public List<EntidadBeneficiaria> getAllEntidades() {
        return repoBeneficiarias.findAll();
    }


    public EntidadBeneficiaria getEntidadById(Long idEntidad) {
        return repoBeneficiarias.findById(idEntidad).orElse(null);
    }

    public void setearCategorias(EntidadBeneficiaria entidad) {
        if (entidad != null) {
            entidad.getNecesidadesActuales().forEach(n -> {
                Categoria categoria = repoCategorias.findById(n.getSubcategoria().getCategoria().getId()).orElse(null);
                n.getSubcategoria().setCategoria(categoria);
            });
        }
    }

    @Transactional
    public void seSatisfaceLaNecesidad(Necesidad necesidadResuelta) {
        EntidadBeneficiaria entidad = getEntidadById(necesidadResuelta.getEntidadBeneficiaria().getId());
        necesidadResuelta.setEstaSatisfechaConDonacion(true);
        repoBeneficiarias.save(entidad);
    }

    public Necesidad buscarNecesidadDeEntidad(Long idNecesidad) {
        return repoBeneficiarias.findNecesidadById(idNecesidad).orElse(null);
    }

    @Transactional
    public EntidadBeneficiaria createEntidad(EntidadBeneficiaria entidad) {

        setearCategorias(entidad);
        repoBeneficiarias.save(entidad);
        return entidad;

    }

    @Transactional
    public EntidadBeneficiaria updateEntidad(Long idEntidad, EntidadBeneficiaria updatedEntidad) {
        EntidadBeneficiaria antigua = repoBeneficiarias.findById(idEntidad).orElse(null);
        antigua.setId(idEntidad);
        antigua.eliminarNecesidades();
        antigua.setNecesidadesActuales(updatedEntidad.getNecesidadesActuales());
        antigua.setDireccion(updatedEntidad.getDireccion());
        antigua.setTelefono(updatedEntidad.getTelefono());
        antigua.setRazonSocial(updatedEntidad.getRazonSocial());
        antigua.setCorreoRepresentante(updatedEntidad.getCorreoRepresentante());
        setearCategorias(antigua);
        repoBeneficiarias.save(antigua);
        return antigua;
    }

    @Transactional
    public String deleteEntidad(Long idEntidad) {
        repoBeneficiarias.deleteById(idEntidad);
        return "Entidad eliminada.";
    }

    @Transactional
    public void asignarDonacionANecesidad(Necesidad necesidad, DonacionSegmentada donacion) {
        Integer cantidadPorEntregar = Math.min(donacion.getBien().getCantidadActual(), necesidad.getCantidadObjetivo());
        necesidad.recibirBienes(cantidadPorEntregar);
        donacion.restarBienesAsignados(cantidadPorEntregar);
        donacion.agregarCantidadDeBienDonado(new BienAsignado(null, cantidadPorEntregar, necesidad.getId(), donacion));
    }


}
