@Service
public class GPSServiceImpl implements GPSService {

    private final GPS_Tool gpsTool;

    public GPSServiceImpl(GPS_Tool gpsTool) {
        this.gpsTool = gpsTool;
    }

    @Override
    public Ruta generarRuta(List<String> direcciones) {
        return gpsTool.planificarRuta(direcciones);
    }
}
