package org.example.donacionesfrontend.dominio.donante;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PersonaJuridica extends Persona {
    private String razonSocial;
    private TipoPersonaJuridica tipoJuridica;
    private String rubro;
    private List<PersonaHumana> representantes = new ArrayList<>();

    public PersonaJuridica(Documento doc, Direccion dir, String razonSocial, TipoPersonaJuridica tipoJuridica, String rubro, List<PersonaHumana> representantes) {
        super();
        setDocumento(doc);
        setDireccion(dir);
        this.razonSocial = razonSocial;
        this.tipoJuridica = tipoJuridica;
        this.rubro = rubro;
        setRepresentantes(representantes);
    }

    @Override
    public void desvincularRelaciones() {
        if (!representantes.isEmpty()) representantes.forEach(personaHumana -> personaHumana.setPersonaJuridica(null));
    }

    public void setRepresentantes(List<PersonaHumana> representantes) {
        this.representantes = representantes;
        representantes.forEach(r -> r.setPersonaJuridica(this));
    }


}