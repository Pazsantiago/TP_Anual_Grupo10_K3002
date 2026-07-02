package Sdonaciones.dominio.necesidad;


import java.time.LocalDate;

public class Periodo {
    private Integer periodoDias;

    public LocalDate getInicioPeriodo() {
        return inicioPeriodo;
    }

    public void setInicioPeriodo(LocalDate inicioPeriodo) {
        this.inicioPeriodo = inicioPeriodo;
    }

    public Integer getPeriodoDias() {
        return periodoDias;
    }

    public void setPeriodoDias(Integer periodoDias) {
        this.periodoDias = periodoDias;
    }

    private LocalDate inicioPeriodo;

}