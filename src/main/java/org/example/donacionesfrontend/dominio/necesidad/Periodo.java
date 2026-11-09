package org.example.donacionesfrontend.dominio.necesidad;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Periodo {
    private Long id;
    private Integer periodoDias;
    private LocalDate inicioPeriodo;

    public Periodo(Integer periodoDias) {
        this.periodoDias = periodoDias;
        this.inicioPeriodo = LocalDate.now();
    }


}