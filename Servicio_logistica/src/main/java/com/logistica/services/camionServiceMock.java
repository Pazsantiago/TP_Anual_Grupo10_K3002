@Service
public class camionServiceMock implements CamionService {

    @Override
    public List<Camion> obtenerCamionesDisponibles() {
        Camion c1 = new Camion("ABC123", 5000, 30, 3.5);
        Camion c2 = new Camion("XYZ789", 8000, 50, 4.0);
        return List.of(c1, c2);
    }
}