package com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donacion.DonacionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DonanteDTO {
    private Long id;
    private List<DonacionDTO> donaciones;
    private MedioContacto contactoPredeterminado;
}
