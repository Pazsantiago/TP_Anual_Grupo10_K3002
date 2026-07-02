package Sdonaciones.dominio.donacion;

import Sdonaciones.dominio.necesidad.Necesidad;

import java.util.Date;

public class DonacionRegistrada {
    private DonacionSegmentada donacion;

    public DonacionSegmentada getDonacion() {
        return donacion;
    }

    public void setDonacion(DonacionSegmentada donacion) {
        this.donacion = donacion;
    }

    public Necesidad getNecesidadResuelta() {
        return necesidadResuelta;
    }

    public void setNecesidadResuelta(Necesidad necesidadResuelta) {
        this.necesidadResuelta = necesidadResuelta;
    }

    private Necesidad necesidadResuelta;

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    private Date fechaHora;
}
