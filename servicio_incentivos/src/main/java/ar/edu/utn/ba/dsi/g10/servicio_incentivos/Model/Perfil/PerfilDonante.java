package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.CategoriasDonante.CategoriaDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Insignia;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PerfilDonante {
    private final long donanteID;
    private CategoriaDonante categoria;
    private int totalDonacionesHistoricas;
    private int totalOrganizacionesAyudadas;
    private Mision misionActual;
    private RachaDonante racha;

    private final List<Insignia> insignias = new ArrayList<>();
    private final List<ProgresoMision> historialMisiones = new ArrayList<>();
    //private RankingPerfil rankingHistoricos;

    public PerfilDonante(long donanteID, CategoriaDonante categoria, Mision misionActual, RankingPerfil rankingHistoricos) {
        this.donanteID = donanteID;
        this.categoria = categoria;
        this.misionActual = misionActual;
        this.racha = new RachaDonante();
        //this.rankingHistoricos = rankingHistoricos;
        inicializarProgreso();
    }

    public double getPorcentajeProgreso() {
        if (historialMisiones.isEmpty()) return 0.0;
        ProgresoMision ultimoProgreso = historialMisiones.getLast();
        if (misionActual != null && ultimoProgreso.getMisionAsociada() != null) {
            // Corrección de getId()
            if (misionActual.getId() == ultimoProgreso.getMisionAsociada().getId()) {
                return ultimoProgreso.getProgresoActual();
            }
        }
        return 0.0;
    }

    public void subirCategoria() {
        if (categoria != null) {
            // Corrección a avanzarASiguienteCategoria()
            categoria.avanzarASiguienteCategoria();
        }
    }

    public void misionCompletada() {
        if (historialMisiones.isEmpty()) return;
        ProgresoMision ultimoProgreso = historialMisiones.getLast();
        if (ultimoProgreso.getCompletada() && misionActual != null && misionActual.getInsignia() != null) {
            boolean yaOtorgada = insignias.stream()
                .anyMatch(insignia -> insignia.getID() == misionActual.getInsignia().getID()); // cambio, antes comparaba con el objeto en memoria, ahora con el id es con la insignia real, tamb podriamos usar hashcode y equals
            if (!yaOtorgada) {
                insignias.add(misionActual.getInsignia());
            }
        }
        //TODO: notificacion insignia
    }

    public ProgresoMision getProgreso() {
        if (historialMisiones.isEmpty()) {
            inicializarProgreso();
        }
        return historialMisiones.getLast(); // correccion sugerida: historialMisiones.lista.get(lista.size() - 1)
    }

    public long getID() {
        return donanteID;
    }
    private void inicializarProgreso() {
        if (misionActual == null) return;
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

    public void incrementarOrganizacionesAyudadas() {
        totalOrganizacionesAyudadas++;
    } // si es la misma organizacion suma otra vez

    public void verificarRachaDonaciones(LocalDate fechaNuevaDonacion) {
        if (this.racha == null) {
            this.racha = new RachaDonante();
        }
        this.racha.registrarDonacion(fechaNuevaDonacion);
    }

    public String getCategoria() {
        return categoria != null ? categoria.getCategoria() : "Sin categoría";
    }

    public List<Insignia> getInsignias() {
        return insignias;
    }

    public int getMisionesCompletadas() {
        return (int) historialMisiones.stream()
                .filter(ProgresoMision::getCompletada)
                .count();
    }
}

