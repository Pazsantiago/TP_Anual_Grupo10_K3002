package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class PersonaJuridica extends Persona {
    private String razonSocial;
    @Enumerated(EnumType.STRING)
    private TipoPersonaJuridica tipoJuridica;
    private String rubro;

    public PersonaJuridica(String razonSocial, TipoPersonaJuridica tipoJuridica, String rubro, List<PersonaHumana> representantes) {
        this.razonSocial = razonSocial;
        this.tipoJuridica = tipoJuridica;
        this.rubro = rubro;
        setRepresentantes(representantes);
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "personaJuridica")
    private List<PersonaHumana> representantes;

    public void setRepresentantes(List<PersonaHumana> representantes) {
        this.representantes = representantes;
        representantes.forEach(r -> r.setPersonaJuridica(this));
    }

}