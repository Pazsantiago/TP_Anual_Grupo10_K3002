package org.example.donacionesfrontend.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaRequestDTO {
    private String tipo;
    private String nombre;
    private Integer edad;
    private Genero genero;
    //
    private String razonSocial;
    private TipoPersonaJuridica tipoJuridica;
    private String rubro;
    private List<Long> representantes = new ArrayList<>();
}
