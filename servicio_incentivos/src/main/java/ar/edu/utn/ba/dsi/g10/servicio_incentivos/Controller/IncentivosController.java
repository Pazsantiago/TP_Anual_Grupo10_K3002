package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Controller;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Insignia;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.ServicioIncentivos;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incentivos")
public class IncentivosController {
    private final ServicioIncentivos servicio;

    public IncentivosController() {
        this(new ServicioIncentivos());
    }

    public IncentivosController(ServicioIncentivos servicio) {
        this.servicio = servicio;
    }

    @PostMapping("/donaciones")
    public ResponseEntity<String> procesarDonacion(
            @RequestParam long donanteId,
            @RequestBody DonacionImportada donacion) {
        servicio.procesarNuevaDonacion(donanteId, donacion);
        return ResponseEntity.ok("Donación procesada correctamente");
    }

    @PostMapping("/donaciones-entregadas")
    public ResponseEntity<String> procesarDonacionEntregada(
            @RequestParam long donanteId,
            @RequestBody DonacionImportada donacion) {
        servicio.procesarDonacionEntregada(donanteId, donacion);
        return ResponseEntity.ok("Donación entregada procesada correctamente");
    }

    @GetMapping("/{donanteId}/mision")
    public ResponseEntity<Mision> obtenerMisionActual(@PathVariable long donanteId) {
        return ResponseEntity.ok(servicio.getMisionActual(donanteId));
    }

    @GetMapping("/{donanteId}/insignias")
    public ResponseEntity<List<Insignia>> obtenerInsignias(@PathVariable long donanteId) {
        return ResponseEntity.ok(servicio.getInsignias(donanteId));
    }

    @GetMapping("/{donanteId}/metricas")
    public ResponseEntity<PerfilDonante> obtenerMetricas(@PathVariable long donanteId) {
        return ResponseEntity.ok(servicio.getMetricas(donanteId));
    }
}
