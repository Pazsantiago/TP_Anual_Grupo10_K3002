package com.grupo10.servicio_donaciones.Sdonaciones.dominio.entidad;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.necesidad.Necesidad;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class EntidadBeneficiaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String razonSocial;
    private String telefono;
    private String correoRepresentante;
    private String direccion;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "entidadBeneficiaria")
    private List<Necesidad> necesidadesActuales = new ArrayList<>();

    public EntidadBeneficiaria(String razonSocial, String telefono, String correoRepresentante, String direccion, List<Necesidad> necesidadesActuales) {
        this.razonSocial = razonSocial;
        this.telefono = telefono;
        this.correoRepresentante = correoRepresentante;
        this.direccion = direccion;
        setNecesidadesActuales(necesidadesActuales);
    }

    public void eliminarNecesidades() {
        this.necesidadesActuales.clear();
    }

    public void setNecesidadesActuales(List<Necesidad> necesidades) {
        this.necesidadesActuales = necesidades;
        setearNecesidadesActuales();
    }

    public void setearNecesidadesActuales() {
        getNecesidadesActuales().forEach(n -> n.setEntidadBeneficiaria(this));
    }

    public void agregarNecesidadActual(Necesidad necesidad) {
        necesidad.setEstaSatisfechaConDonacion(false);
        necesidadesActuales.add(necesidad);
        necesidad.setEntidadBeneficiaria(this);
    }

    public void actualizarNecesidad(Necesidad necesidadActualizada) {
        necesidadesActuales.forEach(n -> {
            if (n.getId().equals(necesidadActualizada.getId())) {
                n = necesidadActualizada;
            }
        });
    }

    public void eliminarNecesidadActual(Long idNecesidad) {
        necesidadesActuales.removeIf(n -> n.getId().equals(idNecesidad));
    }

    public Necesidad obtenerNecesidadPorId(Long idNecesidad) {
        return necesidadesActuales.stream().filter(e -> e.getId().equals(idNecesidad)).findFirst().orElse(null);
    }

    public List<Necesidad> obtenerNecesidadesHistoricas() {
        return necesidadesActuales.stream().filter(n -> n.estaSatisfecha()).toList();
    }

}
