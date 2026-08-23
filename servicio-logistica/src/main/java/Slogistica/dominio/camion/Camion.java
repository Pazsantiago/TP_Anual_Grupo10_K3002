package Slogistica.dominio.camion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un camión de la flota de la organización.
 * Todos los camiones pueden transportar cualquier tipo de bien y
 * siempre parten desde el depósito para realizar las entregas del día.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Camion {

    private Integer id;
    private String patente;
    private Float capacidadVolumen; // m3
    private Float altura;           // m
    private Float capacidadCarga;   // kg
    private boolean disponible = true;
}
