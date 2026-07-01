package Servicio_incentivos.dominio;

import Servicio_incentivos.dominio.CategoriasDonante.CategoriaDonante;
import Servicio_incentivos.dominio.Misiones.Insignia;
import Servicio_incentivos.dominio.Misiones.Mision;
import Servicio_incentivos.dominio.Misiones.ProgresoMision;

import java.util.List;

public class PerfilDonante {
    private long donanteID;
    private CategoriaDonante categoria;
    private int totalDonacionesHistoricas;
    private int totalOrganizacionesAyudadas;
    private Mision misionActual;

    public List<Insignia> getInsignias() {
        return insignias;
    }

    private List<Insignia> insignias;
    private List<ProgresoMision> historialMisiones;
    private RankingPerfil rankingHistoricos;


    public CategoriaDonante getCategoria() {
        return categoria;
    }
    public Mision getMisionActual() {
        return misionActual;
    }
    public double getPorcentajeProgreso() {
        if (historialMisiones == null || historialMisiones.isEmpty()) {
            return 0.0;
        }
        ProgresoMision ultimoProgreso = historialMisiones.getLast();
        if (misionActual != null && ultimoProgreso.getMisionAsociada() != null) {
            if (misionActual.getID() == ultimoProgreso.getMisionAsociada().getID()) {
                return ultimoProgreso.getProgresoActual();
            }
        }
        return 0.0;
    }
    public void subirCategoria(){
       categoria = categoria.siguienteCategoria();
    }

    public void misionCompletada() { //OJO: no esta en el diagrama
        ProgresoMision ultimoProgreso = historialMisiones.getLast();
        if (ultimoProgreso.getCompletada()) {
            this.insignias.add(this.misionActual.getInsignia());
            // publicar en redes sociales (n8n)
        }


    }
    public ProgresoMision getProgreso(){
        return historialMisiones.getLast();
        }
    public long getID() {return donanteID;}
}
