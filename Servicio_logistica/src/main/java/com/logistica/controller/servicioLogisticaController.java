@RestController
@RequestMapping("/api/logistica")
public class ServicioLogisticaController {

    private final ServicioLogistica servicioLogistica;

    public ServicioLogisticaController(ServicioLogistica servicioLogistica) {
        this.servicioLogistica = servicioLogistica;
    }

    @PostMapping("/distribuir")
    public ResponseEntity<String> distribuir(@RequestBody DistribucionRequest request) {
        servicioLogistica.distribuirBultos(request.getCamiones(), request.getBultos());
        return ResponseEntity.ok("Distribución completada");
    }

    @PostMapping("/planificar")
    public ResponseEntity<Ruta> planificar(@RequestBody List<String> direcciones) {
        Ruta ruta = servicioLogistica.planificarRuta(direcciones);
        return ResponseEntity.ok(ruta);
    }
}
