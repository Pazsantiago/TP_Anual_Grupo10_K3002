package com.grupo10.servicio_donaciones.Sdonaciones.dominio.entidad;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.necesidad.Necesidad;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class EntidadBeneficiaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String razonSocial;
    private String telefono;
    private String correoRepresentante;
    private String direccion;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "entidadBeneficiaria")
    private List<Necesidad> necesidades = new ArrayList<>();

    public EntidadBeneficiaria(String razonSocial, String telefono, String correoRepresentante, String direccion, List<Necesidad> necesidades) {
        this.razonSocial = razonSocial;
        this.telefono = telefono;
        this.correoRepresentante = correoRepresentante;
        this.direccion = direccion;
        setNecesidades(necesidades);
    }

    public void eliminarNecesidades() {
        this.necesidades.clear();
    }

    public void setNecesidades(List<Necesidad> necesidades) {
        this.necesidades = necesidades;
        setearNecesidadesActuales();
    }

    public void setearNecesidadesActuales() {
        getNecesidades().forEach(n -> n.setEntidadBeneficiaria(this));
    }

    public void agregarNecesidadActual(Necesidad necesidad) {
        necesidad.setEstaSatisfechaConDonacion(false);
        necesidades.add(necesidad);
        necesidad.setEntidadBeneficiaria(this);
    }

    public void actualizarNecesidad(Necesidad necesidadActualizada) {
        necesidades.forEach(n -> {
            if (n.getId().equals(necesidadActualizada.getId())) {
                necesidades.set(getNecesidades().indexOf(n), necesidadActualizada);
            }
        });
    }

    public void eliminarNecesidadActual(Long idNecesidad) {
        necesidades.removeIf(n -> n.getId().equals(idNecesidad));
    }


    public List<Necesidad> obtenerNecesidadesHistoricas() {
        return necesidades.stream().filter(n -> n.estaSatisfecha()).toList();
    }

}
