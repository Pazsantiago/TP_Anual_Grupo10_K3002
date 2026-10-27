package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class PersonaJuridica extends Persona {
    private String razonSocial;
    @Enumerated(EnumType.STRING)
    private TipoPersonaJuridica tipoJuridica;
    private String rubro;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "personaJuridica")
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
        representantes.forEach(personaHumana -> personaHumana.setPersonaJuridica(null));
    }

    public void setRepresentantes(List<PersonaHumana> representantes) {
        this.representantes = representantes;
        representantes.forEach(r -> r.setPersonaJuridica(this));
    }


}