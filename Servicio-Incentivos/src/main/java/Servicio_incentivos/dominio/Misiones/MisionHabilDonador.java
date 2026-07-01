package Servicio_incentivos.dominio.Misiones;

import Servicio_incentivos.dominio.DonacionImportada;

import java.util.List;

public class MisionHabilDonador extends Mision {
    private double CantidadBienesRequerida = 10.0;

    @Override
    public double calcularProgreso(List<DonacionImportada> historialMision) {
        if (historialMision == null || historialMision.isEmpty()) {
            return 0;
        }
        double contadorBienes = 0;

        for (DonacionImportada donacionInstancia : historialMision) {
            if (contadorBienes >= 10) {
                break;
            }
           contadorBienes += donacionInstancia.getCantidadDonada();
        }
        return ((contadorBienes / CantidadBienesRequerida)*100);
    }
}

