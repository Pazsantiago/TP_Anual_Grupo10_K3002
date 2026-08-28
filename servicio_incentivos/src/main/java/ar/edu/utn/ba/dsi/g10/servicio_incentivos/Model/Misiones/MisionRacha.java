package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;

public class MisionRacha extends Mision {
    private double mesesConsecutivosRequeridos = 10.0;

    //TODO: el progreso de mision racha funciona, igual revisar

    @Override
    public double calcularProgreso(List<DonacionImportada> historialMision) {
        if (historialMision == null || historialMision.isEmpty()) {
            return 0.0;
        }

        // 1. Filtrar solo donaciones exitosas y ordenarlas cronológicamente
        List<DonacionImportada> exitosasOrdenadas = historialMision.stream()
                .filter(DonacionImportada::isExitosa)
                .sorted(Comparator.comparing(DonacionImportada::getFechaDonacion))
                .toList();

        if (exitosasOrdenadas.isEmpty()) return 0.0;

        double contadorMeses = 0.0;
        YearMonth mesUltimaDonacion = null;

        for (DonacionImportada donacion : exitosasOrdenadas) {
            YearMonth mesDonacion = YearMonth.from(donacion.getFechaDonacion());

            if (mesUltimaDonacion == null) {
                contadorMeses = 1.0;
            } else if (mesDonacion.equals(mesUltimaDonacion)) {
                continue; // Mismo mes: no suma ni resetea
            } else if (mesDonacion.equals(mesUltimaDonacion.plusMonths(1))) {
                contadorMeses += 1.0; // Mes consecutivo
            } else if (mesDonacion.isAfter(mesUltimaDonacion.plusMonths(1))) {
                contadorMeses = 1.0; // Se rompió la racha: reinicia
            }

            mesUltimaDonacion = mesDonacion;
            if (contadorMeses >= mesesConsecutivosRequeridos) break;
        }

        // 2. Comprobar si la racha caducó respecto a la fecha actual
        YearMonth mesActual = YearMonth.now();
        if (mesUltimaDonacion != null && mesActual.isAfter(mesUltimaDonacion.plusMonths(1))) {
            contadorMeses = 0.0;
        }

        double porcentaje = (contadorMeses / mesesConsecutivosRequeridos) * 100.0;
        return Math.min(100.0, porcentaje);
    }
  
}
