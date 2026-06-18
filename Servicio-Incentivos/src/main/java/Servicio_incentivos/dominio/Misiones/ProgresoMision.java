package Servicio_incentivos.dominio.Misiones;

import java.time.LocalDate;

public class ProgresoMision {
    private Mision MisionAsociada;
    private double progresoActual;
    private LocalDate fechaInicio;
    private LocalDate fechaCompletado;
    private boolean completada;

    public void reiniciar() {}
    public void actualizar(double progreso) {
        progresoActual = progresoActual + progreso; //decidir si se suma o se setea otro valor
        if (progresoActual == 100) this.marcarCompletada();

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
