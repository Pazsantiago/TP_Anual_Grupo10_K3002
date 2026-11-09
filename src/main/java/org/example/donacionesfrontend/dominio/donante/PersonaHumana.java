package org.example.donacionesfrontend.dominio.donante;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonaHumana extends Persona {
    private Long id;
    private String nombre;
    private Integer edad;
    private Genero genero;
    @JsonIgnore
    private PersonaJuridica personaJuridica = null;

}