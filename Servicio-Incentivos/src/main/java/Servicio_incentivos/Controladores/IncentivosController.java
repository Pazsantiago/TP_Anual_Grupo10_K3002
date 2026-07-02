package Servicio_incentivos.Controladores;

import Servicio_incentivos.Servicios.ServicioIncentivos;
import Servicio_incentivos.dominio.DonacionImportada;
import Servicio_incentivos.dominio.Misiones.Insignia;
import Servicio_incentivos.dominio.Misiones.Mision;
import Servicio_incentivos.dominio.PerfilDonante;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
