@RestController
@RequestMapping("/api/distribucion")
public class distribucionDeCargasController {

    private final DistribucionDeCargasService distribucionDeCargasService;

    public distribucionDeCargasController(DistribucionDeCargasService distribucionDeCargasService) {
        this.distribucionDeCargasService = distribucionDeCargasService;
    }

    @PostMapping("/asignar")
    public ResponseEntity<Map<Camion, List<Bulto>>> asignarBultos(@RequestBody DistribucionRequest request) {
        Map<Camion, List<Bulto>> resultado = distribucionDeCargasService.asignarBultos(request.getCamiones(), request.getBultos());
        return ResponseEntity.ok(resultado);
    }
}
