@RestController
@RequestMapping("/api/callback")
public class gpsCallbackController {

    private final gpsService gpsService;

    public gpsCallbackController(gpsService gpsService) {
        this.gpsService = gpsService;
    }

    @PostMapping("/resultado")
    public ResponseEntity<String> recibirResultado(@RequestBody RutaResponse rutaResponse) {
        gpsService.registrarRuta(rutaResponse);
        return ResponseEntity.ok("Ruta recibida y registrada correctamente");
    }
}
