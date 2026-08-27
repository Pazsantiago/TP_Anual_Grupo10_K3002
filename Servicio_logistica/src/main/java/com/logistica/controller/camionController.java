@RestController
@RequestMapping("/api/camiones")   // Prefijo de ruta
public class CamionController {

    private final CamionService camionService;

    public CamionController(CamionService camionService) {
        this.camionService = camionService;
    }

    @GetMapping
    public List<Camion> listarCamiones() {
        return camionService.obtenerCamionesDisponibles();
    }

    @PostMapping
    public Camion registrarCamion(@RequestBody Camion camion) {
        return camionService.guardar(camion);
    }
}
