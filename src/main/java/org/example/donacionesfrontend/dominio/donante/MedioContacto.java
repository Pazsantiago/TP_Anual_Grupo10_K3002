package org.example.donacionesfrontend.dominio.donante;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(exclude = {"donante"})
@NoArgsConstructor
public class MedioContacto {
    private Long id;
    private MedioNotificacion tipo;
    private String correoElectronico;
    private String telefono;
    private boolean esPredeterminado;
    @JsonIgnore
    private Donante donante;
}
