package Servicio_incentivos.dominio.Misiones;

import Servicio_incentivos.dominio.DonacionImportada;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MisionDonacionesExitosas extends Mision {
    private double donacionesExitosasRequeridas = 10.0;
    @Override
    public double calcularProgreso(List<DonacionImportada> historialMision) {
        if (historialMision == null || historialMision.isEmpty()) {
            return 0;
        }
        double contadorExitosas = 0;

        for (DonacionImportada donacionInstancia : historialMision) {
            if (contadorExitosas >= 10) {
                break;
            }
            if (donacionInstancia.isExitosa()) {
                contadorExitosas += 1;
            }
        }
        return ((contadorExitosas / donacionesExitosasRequeridas)*100);
    }
}
