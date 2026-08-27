@Service
public class ServicioLogisticaImpl implements ServicioLogistica {

    private final DistribucionDeCargasService distribucionDeCargasService;
    private final GPS_Tool gpsTool;
    private final NotificacionService notificacionService;
}

    public ServicioLogisticaImpl(DistribucionDeCargasService distribucionDeCargasService,
                                 GPS_Tool gpsTool,
                                 NotificacionService notificacionService) {
        this.distribucionDeCargasService = distribucionDeCargasService;
        this.gpsTool = gpsTool;
        this.notificacionService = notificacionService;
    }

    @Override
    public void distribuirBultos(List<Camion> camiones, List<Bulto> bultos) {
        Map<Camion, List<Bulto>> asignaciones = distribucionDeCargasService.asignarBultos(camiones, bultos);
        asignaciones.forEach((camion, listaBultos) -> {
            System.out.println("Camión " + camion.getPatente() + " asignado con " + listaBultos.size() + " bultos.");
        });
    }

    @Override
    public Ruta planificarRuta(List<String> direcciones) {
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
    }

