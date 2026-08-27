package Sdonaciones.dominio.donacion;

import Sdonaciones.dominio.necesidad.Necesidad;
import lombok.Data;

import java.util.Date;

@Data
public class DonacionAsignada {
    private DonacionSegmentada donacion;
    private int id;
    private Necesidad necesidadResuelta;
    private Date fechaHora;

    public DonacionAsignada(DonacionSegmentada donacion, Necesidad necesidad, Date fechaHora) {
        this.donacion = donacion;
        this.necesidadResuelta = necesidad;
        this.fechaHora = fechaHora;
    }
}
