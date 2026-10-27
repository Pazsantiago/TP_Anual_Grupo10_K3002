package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class PersonaHumana extends Persona {
    private Long id;
    private String nombre;
    private Integer edad;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    @ManyToOne()
    @JsonIgnore
    private PersonaJuridica personaJuridica = null;

}