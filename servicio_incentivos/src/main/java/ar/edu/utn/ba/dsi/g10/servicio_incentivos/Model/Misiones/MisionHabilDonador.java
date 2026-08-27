package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MisionHabilDonador extends Mision {
    private double cantidadBienesRequerida;

    @Override
    public double calcularProgreso(List<DonacionImportada> historialMision) {
        if (historialMision == null || historialMision.isEmpty()) {
            return 0;
        }
        double contadorBienes = 0;

        for (DonacionImportada donacionInstancia : historialMision) {
            if (contadorBienes >= cantidadBienesRequerida) {
                break;
            }
            contadorBienes += donacionInstancia.getCantidadDonada();
        }
        return ((contadorBienes / cantidadBienesRequerida)*100);
    }
}
