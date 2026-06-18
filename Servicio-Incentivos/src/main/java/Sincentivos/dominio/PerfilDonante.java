package Sincentivos.dominio;

import java.util.List;

public class PerfilDonante {
    private long donanteID;
    private CategoriaDonante categoria;
    private int totalDonacionesHistoricas;
    private int totalOrganizacionesAyudadas;
    private Mision misionActual;
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
        ProgresoMision ultimoProgreso = historialMisiones.get(historialMisiones.size() - 1);
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
        ProgresoMision ultimoProgreso = historialMisiones.get(historialMisiones.size() - 1);
        if (ultimoProgreso.getCompletada()) {
            this.insignias.add(this.misionActual.getInsignia());
            // publicar en redes sociales (n8n)
        }


    }
}
