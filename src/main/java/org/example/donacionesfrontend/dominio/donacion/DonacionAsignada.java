package org.example.donacionesfrontend.dominio.donacion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.donacionesfrontend.dominio.necesidad.Necesidad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter

@NoArgsConstructor
public class DonacionAsignada {
    private Long id;
    private List<DonacionSegmentada> donacionesSegmentadas = new ArrayList<>();
    private Necesidad necesidadResuelta;
    private Date fechaHora = new Date();
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
        if (!donacionesSegmentadas.isEmpty()) this.donacionesSegmentadas.forEach(s -> s.eliminarAsignada(this.getId()));
    }


}
