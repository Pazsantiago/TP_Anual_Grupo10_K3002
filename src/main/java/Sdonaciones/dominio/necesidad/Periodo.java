package Sdonaciones.dominio.necesidad;


import java.time.LocalDate;

public class Periodo {
    private int periodoDias;

    public LocalDate getInicioPeriodo() {
        return inicioPeriodo;
    }

    public void setInicioPeriodo(LocalDate inicioPeriodo) {
        this.inicioPeriodo = inicioPeriodo;
    }

    public int getPeriodoDias() {
        return periodoDias;
    }

    public void setPeriodoDias(int periodoDias) {
        this.periodoDias = periodoDias;
    }

    private LocalDate inicioPeriodo;

}