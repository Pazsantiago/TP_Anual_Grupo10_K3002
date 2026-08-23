package cLogistica;

import Slogistica.dominio.donacion.DonacionDTO;
import Slogistica.dominio.ruta.PlanDeRuta;
import Slogistica.gestor.GestorLogistica;
import Slogistica.planificador.dto.CallbackPlanificacionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logistica/planificacion")
public class CPlanificacion {

    private final GestorLogistica gestorLogistica;

    public CPlanificacion(GestorLogistica gestorLogistica) {
        this.gestorLogistica = gestorLogistica;
    }

    /**
     * Punto de entrada desde el Servicio de Donaciones: envía las
     * donaciones en estado "Asignación Realizada" para que se les
     * planifique una entrega durante la siguiente jornada operativa.
     */
    @PostMapping("/iniciar")
    public ResponseEntity<String> iniciarPlanificacion(
            @RequestBody List<DonacionDTO> donacionesAsignadas,
            @RequestParam(defaultValue = "http://localhost:8081/api/logistica/planificacion/callback") String callbackUrl) {
        gestorLogistica.solicitarPlanificacionDeRutas(donacionesAsignadas, callbackUrl);
        return ResponseEntity.accepted().body("Planificación solicitada al proveedor externo.");
    }

    /**
     * URL de callback que invoca el proveedor externo de planificación de
     * rutas para notificar el resultado (lista ordenada de destinos por
     * camión).
     */
    @PostMapping("/callback")
    public ResponseEntity<List<PlanDeRuta>> recibirPlanificacion(@RequestBody CallbackPlanificacionDTO resultado) {
        List<PlanDeRuta> planes = gestorLogistica.registrarPlanificacionRecibida(resultado.getRutas());
        return ResponseEntity.ok(planes);
    }
}
