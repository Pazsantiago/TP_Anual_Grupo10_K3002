package Sdonaciones.dominio.donante;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonaHumana extends Persona {
    private String nombre;
    private Integer edad;
    private Genero genero;

}