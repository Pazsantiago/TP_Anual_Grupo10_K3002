package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProgresoMision {
    private Mision MisionAsociada;
    private double progresoActual;
    private List<DonacionImportada> historialDonaciones = new ArrayList<>();
    private LocalDate fechaInicio;
    private LocalDate fechaCompletado;
    private boolean completada;

    public void reiniciar() {
        historialDonaciones.clear();
        progresoActual = 0.0;
        completada = false;
        fechaCompletado = null;
    }

    public void actualizar(DonacionImportada donacionImportada) {
        if (completada) {
            return;
        }

        if (donacionImportada == null || MisionAsociada == null) {
            return;
        }

        historialDonaciones.add(donacionImportada);

        progresoActual = MisionAsociada.calcularProgreso(
            historialDonaciones
        );

        if (progresoActual >= 100.0) {
            marcarCompletada();
        }
    }

    public void marcarCompletada() {
        completada = true;
        if (fechaCompletado == null) {
            fechaCompletado = LocalDate.now();
        }
    }

    public boolean getCompletada() {
        return completada;
    }

}

