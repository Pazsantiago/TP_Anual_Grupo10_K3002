package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class PersonaHumana extends Persona {
    private Long id;
    private String nombre;
    private Integer edad;

    public PersonaHumana(Documento documento, Direccion direccion, String nombre, Integer edad, Genero genero) {
        super(documento, direccion);
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
        this.personaJuridica = null;
    }

    @Enumerated(EnumType.STRING)
    private Genero genero;
    @ManyToOne()
    @JsonIgnore
    private PersonaJuridica personaJuridica;
}