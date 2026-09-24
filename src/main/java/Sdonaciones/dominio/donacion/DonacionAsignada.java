package Sdonaciones.dominio.donacion;

import Sdonaciones.dominio.necesidad.Necesidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonacionAsignada {
    private Long id;
    private List<DonacionSegmentada> donacionesSegmentadas;
    private Necesidad necesidadResuelta;
    private Date fechaHora;
    private AsignacionCompleta asignacionCompleta;

    public void agregarDonacionSegmentada(DonacionSegmentada donacionSegmentada) {
        this.donacionesSegmentadas.add(donacionSegmentada);
    }


}
