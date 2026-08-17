package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;

import java.util.List;

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
