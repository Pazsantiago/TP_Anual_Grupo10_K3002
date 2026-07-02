package Servicio_incentivos.dominio.Misiones;

import java.time.LocalDate;
import java.util.List;
import Servicio_incentivos.dominio.DonacionImportada;

public class MisionRacha extends Mision {
    private double mesesConsecutivosRequeridos = 10.0;

    @Override
    public double calcularProgreso(List<DonacionImportada> historialMision) {
        if (historialMision == null || historialMision.isEmpty()) {
            return 0;
        }
        double contadorMeses = 0.0;
        LocalDate fechaInstancia = null;

        for (DonacionImportada donacionInstancia : historialMision) {
            if (contadorMeses >= 10) {
                break;
            }
        if(fechaInstancia == null) {
            fechaInstancia = donacionInstancia.getFechaDonacion();
           contadorMeses += 1;
        }
        if(fechaInstancia.isBefore(donacionInstancia.getFechaDonacion().minusMonths(1))) {
            contadorMeses += 1;
            fechaInstancia = donacionInstancia.getFechaDonacion();
        }
    }
    return ((contadorMeses / mesesConsecutivosRequeridos)*100);
    }
}