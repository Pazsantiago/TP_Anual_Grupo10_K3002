package Sdonaciones.dominio.donante;

import Sdonaciones.dominio.donacion.Donacion;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DonanteDTO {
    private Long id;
    private List<Donacion> donaciones;
    private MedioContacto contactoPredeterminado;
}
