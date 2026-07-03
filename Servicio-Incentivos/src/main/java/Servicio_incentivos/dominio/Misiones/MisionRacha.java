package Servicio_incentivos.dominio.Misiones;

import java.time.LocalDate;
import java.time.YearMonth;
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
           continue;
        }
            YearMonth mesUltimaDonacion = YearMonth.from(fechaInstancia);
            YearMonth mesDonacionActual = YearMonth.from(donacionInstancia.getFechaDonacion());

            if (mesDonacionActual.equals(mesUltimaDonacion.plusMonths(1))) {
                contadorMeses += 1;
            }
            else if (mesDonacionActual.isAfter(mesUltimaDonacion.plusMonths(1))) {
                contadorMeses = 1;
            }
            fechaInstancia = donacionInstancia.getFechaDonacion();
    }
        return ((contadorMeses / mesesConsecutivosRequeridos)*100);
}
}