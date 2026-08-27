@Service
public class BultoServiceMock implements BultoService {

    @Override
    public List<Bulto> obtenerBultosPendientes() {
        Bulto b1 = new Bulto(1L, 200, 2.5, 1.2);
        Bulto b2 = new Bulto(2L, 300, 3.0, 1.5);
        return List.of(b1, b2);
    }
}
