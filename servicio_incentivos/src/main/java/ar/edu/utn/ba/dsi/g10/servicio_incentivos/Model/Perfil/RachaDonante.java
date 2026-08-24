package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RachaDonante {
    private int rachaActual;
    private int rachaMaxima;
    private LocalDate fechaUltimaDonacion;
    private boolean estaActiva;

    public boolean estaVigente(LocalDate fechaNueva) {
        if (this.fechaUltimaDonacion == null) {
            return true;
        }
        long diasTranscurridos = ChronoUnit.DAYS.between(this.fechaUltimaDonacion, fechaNueva);
        return diasTranscurridos >= 0 && diasTranscurridos <= 30;
    }

    public void registrarDonacion(LocalDate fechaDonacion) {
        if (fechaDonacion == null) return;

        if (estaVigente(fechaDonacion)) {
            if (this.fechaUltimaDonacion == null || !fechaDonacion.isEqual(this.fechaUltimaDonacion)) {
                this.rachaActual++;
            }
        } else {
            this.rachaActual = 1;
        }

        this.fechaUltimaDonacion = fechaDonacion;
        this.estaActiva = true;

        if (this.rachaActual > this.rachaMaxima) {
            this.rachaMaxima = this.rachaActual;
        }
    }
}
