package Slogistica.planificador.dto;

import Slogistica.dominio.donacion.DonacionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/** Ruta ordenada de destinos para un camión, tal como la devuelve el proveedor externo. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RutaPlanificadaDTO {

    private String patenteCamion;
    private List<DonacionDTO> destinosOrdenados;
}
