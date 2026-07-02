package Servicio_incentivos.dominio;

import Servicio_incentivos.dominio.CategoriasDonante.CategoriaDonante;
import Servicio_incentivos.dominio.Misiones.Insignia;
import Servicio_incentivos.dominio.Misiones.Mision;
import Servicio_incentivos.dominio.Misiones.ProgresoMision;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PerfilDonante {
    private final long donanteID;
    private CategoriaDonante categoria;
    private int totalDonacionesHistoricas;
    private int totalOrganizacionesAyudadas;
    private LocalDate fechaUltimaDonacion;
    private int rachaDonaciones;
    private Mision misionActual;
    private final List<Insignia> insignias = new ArrayList<>();
    private final List<ProgresoMision> historialMisiones = new ArrayList<>();
    private RankingPerfil rankingHistoricos;

    public PerfilDonante(long donanteID, CategoriaDonante categoria, Mision misionActual) {
        this.donanteID = donanteID;
        this.categoria = categoria;
        this.misionActual = misionActual;
        inicializarProgreso();
    }

    public List<Insignia> getInsignias() {
        return insignias;
    }

    public CategoriaDonante getCategoria() {
        return categoria;
    }

    public Mision getMisionActual() {
        return misionActual;
    }

    public double getPorcentajeProgreso() {
        if (historialMisiones.isEmpty()) {
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

    public void subirCategoria() {
        if (categoria != null && categoria.siguienteCategoria() != null) {
            categoria = categoria.siguienteCategoria();
        }
    }

    public void misionCompletada() {
        if (historialMisiones.isEmpty()) {
            return;
        } 
        ProgresoMision ultimoProgreso = historialMisiones.getLast();
        if (ultimoProgreso.getCompletada() && misionActual != null && misionActual.getInsignia() != null) {
            boolean yaOtorgada = insignias.stream().anyMatch(insignia -> insignia == misionActual.getInsignia());
            if (!yaOtorgada) {
                insignias.add(misionActual.getInsignia());
            }
        }
    }

    public ProgresoMision getProgreso() {
        if (historialMisiones.isEmpty()) {
            inicializarProgreso();
        }
        return historialMisiones.getLast();
    }

    public long getID() {
        return donanteID;
    }

    private void inicializarProgreso() {
        if (misionActual == null) {
            return;
        }
        if (historialMisiones.isEmpty()) {
            ProgresoMision progresoInicial = new ProgresoMision();
            progresoInicial.setMisionAsociada(misionActual);
            progresoInicial.setProgresoActual(0.0);
            historialMisiones.add(progresoInicial);
        }
    }
    public void sumarDonacion() {
        totalDonacionesHistoricas++;
    }
    public void setUltimaDonacion(LocalDate fecha) {
        this.fechaUltimaDonacion = fecha;
    }
    public void verificarRachaDonaciones(LocalDate fechaNuevaDonacion) {
        if (fechaNuevaDonacion != null) {
            if (fechaNuevaDonacion.minusMonths(1).isAfter(this.fechaUltimaDonacion)) {
                rachaDonaciones++;
            } 
        } else {
            return;
        }
    }
}
