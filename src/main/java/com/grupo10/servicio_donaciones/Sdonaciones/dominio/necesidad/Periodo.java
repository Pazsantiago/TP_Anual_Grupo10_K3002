package com.grupo10.servicio_donaciones.Sdonaciones.dominio.necesidad;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Periodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer periodoDias;
    private LocalDate inicioPeriodo;

    public Periodo(Integer periodoDias, LocalDate inicioPeriodo) {
        this.periodoDias = periodoDias;
        this.inicioPeriodo = inicioPeriodo;
    }


}