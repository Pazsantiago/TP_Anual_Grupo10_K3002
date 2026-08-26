@Service
public class GPSServiceImpl implements GPSService {

    private final RutaRepository rutaRepository;
    private final EntregaRepository entregaRepository;

    public GPSServiceImpl(RutaRepository rutaRepository, EntregaRepository entregaRepository) {
        this.rutaRepository = rutaRepository;
        this.entregaRepository = entregaRepository;
    }

    @Override
    public void registrarRuta(RutaResponse rutaResponse) {
        Ruta ruta = new Ruta();
        ruta.setCamion(rutaResponse.getCamion());
        ruta.setDestinos(rutaResponse.getDestinos());
        ruta.setFecha(LocalDate.now().plusDays(1));

        rutaRepository.save(ruta);

        // Actualizar estado de las entregas asignadas
        for (Entrega entrega : rutaResponse.getEntregas()) {
            entrega.setEstado(EstadoEntrega.ASIGNADA);
            entregaRepository.save(entrega);
        }
    }
}
