@RestController
@RequestMapping("/api/rutas")
public class GPSController {

    private final GPSService gpsService;

    public GPSController(GPSService gpsService) {
        this.gpsService = gpsService;
    }

    @PostMapping("/planificar")
    public ResponseEntity<Ruta> planificarRuta(@RequestBody List<String> direcciones) {
        Ruta ruta = gpsService.generarRuta(direcciones);
        return ResponseEntity.ok(ruta);
    }
}
