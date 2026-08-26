@RequestMapping("/api/rutas")
public class PlanRutaController {

    @Autowired
    private PlanRutaService planRutaService;

    @PostMapping("/generar")
    public ResponseEntity<PlanRuta> generarRuta(@RequestBody List<Direccion> direcciones) {
    PlanRuta planRuta = planRutaService.generarPlanRuta(direcciones);
    return ResponseEntity.ok(planRuta);
}
}
