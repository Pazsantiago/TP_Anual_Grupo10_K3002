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

    // antes comparabamos hasta 30 dias, no contemplabamos los meses con 31 dias, en algunos casos la racha se hubiese contabilizado mal
    // ahora comparamos directamente con el mes, sea cual sea

    public void registrarDonacion(LocalDate fechaDonacion) {
        if (fechaDonacion == null) return;

        YearMonth nuevoMes = YearMonth.from(fechaDonacion);

        if (fechaUltimaDonacion == null) {
            rachaActual = 1;
        } else {
            YearMonth ultimoMes = YearMonth.from(fechaUltimaDonacion);

            if (nuevoMes.isBefore(ultimoMes)) {
                return; // donacion vieja
            }

            if (nuevoMes.equals(ultimoMes)) {
                return; // ya dono ese mes
            }

            if (nuevoMes.equals(ultimoMes.plusMonths(1))) {
                rachaActual++;
            } else {
                rachaActual = 1;
            }
        }

        fechaUltimaDonacion = fechaDonacion;
        rachaMaxima = Math.max(rachaMaxima, rachaActual);
    }

    public boolean estaVigente(LocalDate fechaActual) {
        if (fechaUltimaDonacion == null) return false;

        YearMonth ultimoMes = YearMonth.from(fechaUltimaDonacion);
        YearMonth mesActual = YearMonth.from(fechaActual);

        return !mesActual.isAfter(ultimoMes.plusMonths(1));
    }

    /*
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
    */
}
