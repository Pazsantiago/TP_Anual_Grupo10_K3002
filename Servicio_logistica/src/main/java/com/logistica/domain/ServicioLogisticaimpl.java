@Service
public class ServicioLogisticaImpl implements ServicioLogistica {

    private final DistribucionDeCargasService distribucionDeCargasService;
    private final GPS_Tool gpsTool;
    private final NotificacionService notificacionService;
    private final CamionRepository camionRepository;
    private final DonacionRepository donacionRepository;
    private final EntregaRepository entregaRepository;
    private final RutaRepository rutaRepository
}

    public ServicioLogisticaImpl(DistribucionDeCargasService distribucionDeCargasService,
                                 GPS_Tool gpsTool,
                                 NotificacionService notificacionService,
                                 CamionRepository camionRepository,
                                 DonacionRepository donacionRepository,
                                 EntregaRepository entregaRepository,
                                 RutaRepository rutaRepository) {
        this.distribucionDeCargasService = distribucionDeCargasService;
        this.gpsTool = gpsTool;
        this.notificacionService = notificacionService;
        this.camionRepository = camionRepository;
        this.donacionRepository = donacionRepository;
        this.entregaRepository = entregaRepository;
        this.rutaRepository = rutaRepository;
    }

    @Override
    public void distribuirBultos(List<Camion> camiones, List<Bulto> bultos) {
        Map<Camion, List<Bulto>> asignaciones = distribucionDeCargasService.asignarBultos(camiones, bultos);
        asignaciones.forEach((camion, listaBultos) -> {
            System.out.println("Camión " + camion.getPatente() + " asignado con " + listaBultos.size() + " bultos.");
        });
        // Guardar asignaciones en BD
        camiones.forEach(camionRepository::save);
    }

    @Override
    public Ruta planificarRuta(List<String> direcciones) {
        Ruta ruta = new Ruta();
        ruta.setDestinos(direcciones);
        rutaRepository.save(ruta);
        Ruta ruta = gpsTool.planificarRuta(direcciones);
        notificacionService.notificarInicioRuta(ruta);
        return ruta;
    }

    @Override
    public void iniciarRuta(Ruta ruta) {
        ruta.setEstado("En tránsito");
        notificacionService.notificarChofer(ruta);
    }

    @Override
    public void registrarEntrega(Entrega entrega) {
        entrega.setEstado("Entregada");
        notificacionService.notificarEntrega(entrega);
        entregaRepository.save(entrega);
    }

