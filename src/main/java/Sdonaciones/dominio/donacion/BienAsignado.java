package Sdonaciones.dominio.donacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BienAsignado {
    private Integer cantidadEntregada;
    private Long idNecesidadAsignada;
}
