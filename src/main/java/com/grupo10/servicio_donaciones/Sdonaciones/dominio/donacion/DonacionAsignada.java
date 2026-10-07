package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.necesidad.Necesidad;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class DonacionAsignada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<DonacionSegmentada> donacionesSegmentadas;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Necesidad necesidadResuelta;
    private Date fechaHora;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private AsignacionCompleta asignacionCompleta;

    public DonacionAsignada(List<DonacionSegmentada> donacionesSegmentadas, Necesidad necesidadResuelta, AsignacionCompleta asignacionCompleta) {
        setDonacionesSegmentadas(donacionesSegmentadas);
        this.necesidadResuelta = necesidadResuelta;
        this.fechaHora = new Date();
        this.asignacionCompleta = asignacionCompleta;
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

    public DonacionSegmentada buscarDonacionSegmentadaPorId(Long idSegmentada) {
        return donacionesSegmentadas.stream().filter(d -> d.getId().equals(idSegmentada)).findFirst().orElse(null);
    }
}
