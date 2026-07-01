package Servicio_incentivos.dominio.Misiones;

import Servicio_incentivos.dominio.DonacionImportada;

import java.time.LocalDate;
import java.util.List;

public class ProgresoMision {
    private Mision MisionAsociada;
    private double progresoActual;
    // propuesto: historial de donaciones hechas hacia este progreso especifico
    private List<DonacionImportada> historialDonaciones;
    //justificacion: de esta manera, se permiten hacer mas comparaciones o pasar progreso de una mision a otra sin que sea un simple numero
    private LocalDate fechaInicio;
    private LocalDate fechaCompletado;
    private boolean completada;

    public void reiniciar() {}
    public void actualizar(DonacionImportada donacionImportada) {
        historialDonaciones.add(donacionImportada);
        progresoActual= MisionAsociada.calcularProgreso(historialDonaciones);
        if (progresoActual >= 100) this.marcarCompletada();

    }
    public void marcarCompletada() {completada = true;}

    public boolean getCompletada() { return completada;}
    public Mision getMisionAsociada() {
        return MisionAsociada;
    }
    public void setMisionAsociada(Mision misionAsociada) {
        MisionAsociada = misionAsociada;
    }
    public double getProgresoActual(){
        return progresoActual;
    }
    public void setProgresoActual(double valor){
        progresoActual = valor;
    }
}
