package Servicio_incentivos.dominio.Misiones;

import Servicio_incentivos.dominio.DonacionImportada;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProgresoMision {
    private Mision MisionAsociada;
    private double progresoActual;
    private List<DonacionImportada> historialDonaciones = new ArrayList<>();
    private LocalDate fechaInicio;
    private LocalDate fechaCompletado;
    private boolean completada;

    public void reiniciar() {
        historialDonaciones.clear();
        progresoActual = 0.0;
        completada = false;
        fechaCompletado = null;
    }

    public void actualizar(DonacionImportada donacionImportada) {
        if (donacionImportada == null || MisionAsociada == null) {
            return;
        }
        historialDonaciones.add(donacionImportada);
        progresoActual = MisionAsociada.calcularProgreso(historialDonaciones);
        if (progresoActual >= 100) {
            marcarCompletada(); // esto quiza lo deberia hacer la mision
            fechaCompletado = LocalDate.now();        
        }
    }

    public void marcarCompletada() {
        completada = true;
        if (fechaCompletado == null) {
            fechaCompletado = LocalDate.now();
        }
    }

    public boolean getCompletada() {
        return completada;
    }

    public Mision getMisionAsociada() {
        return MisionAsociada;
    }

    public void setMisionAsociada(Mision misionAsociada) {
        MisionAsociada = misionAsociada;
    }

    public double getProgresoActual() {
        return progresoActual;
    }

    public void setProgresoActual(double valor) {
        progresoActual = valor;
    }
}
