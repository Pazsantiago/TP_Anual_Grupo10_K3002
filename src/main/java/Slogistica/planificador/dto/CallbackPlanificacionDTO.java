package Slogistica.planificador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/** Body que el proveedor externo envía a la URL de callback con el resultado de la planificación. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallbackPlanificacionDTO {

    private List<RutaPlanificadaDTO> rutas;
}
