package org.example.broker.dominio.donacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DonacionAsignadaDTO {
    private Long idAsignada;
    private Long idEntidad;
    private List<Long> idDonantes;
    private List<Long> idSegmentadas;
    private String direccionEntidad;
}
