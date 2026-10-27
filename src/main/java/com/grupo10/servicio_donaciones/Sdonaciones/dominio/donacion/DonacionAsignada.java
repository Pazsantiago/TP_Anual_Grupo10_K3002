package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.necesidad.Necesidad;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class DonacionAsignada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<DonacionSegmentada> donacionesSegmentadas = new ArrayList<>();
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Necesidad necesidadResuelta;
    private Date fechaHora = new Date();
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private AsignacionCompleta asignacionCompleta;

    public DonacionAsignada(List<DonacionSegmentada> donacionesSegmentadas, Necesidad necesidadResuelta) {
        this.necesidadResuelta = necesidadResuelta;
        setDonacionesSegmentadas(donacionesSegmentadas);
    }

    public Boolean sePuedePlanificarEnvio() {
        Integer cantDonadaDeNecesidad = 0;
        for (DonacionSegmentada donacion : this.donacionesSegmentadas) {
            List<BienAsignado> bienesDeEstaNecesidad = donacion.getBienesAsignados().stream().filter(b -> b.getIdNecesidadAsignada().equals(this.getNecesidadResuelta().getId())).toList();
            cantDonadaDeNecesidad += bienesDeEstaNecesidad.stream().map(b -> b.getCantidadEntregada()).toList().stream().mapToInt(Integer::intValue).sum();
        }
        return cantDonadaDeNecesidad.equals(this.necesidadResuelta.getCantidadObjetivo());
    }

    public void setDonacionesSegmentadas(List<DonacionSegmentada> donacionesSegmentadas) {
        this.donacionesSegmentadas = donacionesSegmentadas;
        setearDonacionesSegmentadas();
    }

    public void setearDonacionesSegmentadas() {
        getDonacionesSegmentadas().forEach(s -> {
            s.agregarDonacionAsignada(this);
        });
    }

    public void agregarDonacionSegmentada(DonacionSegmentada donacionSegmentada) {
        this.donacionesSegmentadas.add(donacionSegmentada);
        donacionSegmentada.agregarDonacionAsignada(this);
    }


    public void eliminarSegmentada(Long idSegmentada) {
        this.donacionesSegmentadas.removeIf(s -> s.getId().equals(idSegmentada));
    }

    public void desvincularSegmentadas() {
        this.donacionesSegmentadas.forEach(s -> s.eliminarAsignada(this.getId()));
    }


}
