package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DonacionAsignadaDTO {
    private Long idAsignada;
    private Long idEntidad;
    private List<Long> idDonantes = new ArrayList<>();
    ;
    private String direccionEntidad;
}
