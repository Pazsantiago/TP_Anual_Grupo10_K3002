package org.example.donacionesfrontend.dtos.request;

import com.grupo10.servicio_donaciones.donaciones.dominio.donante.Genero;
import com.grupo10.servicio_donaciones.donaciones.dominio.donante.TipoPersonaJuridica;
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
