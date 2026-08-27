@ExtendWith(MockitoExtension.class)
class ServicioLogisticaImplTest {

    @Mock
    private DistribuidorDeCargasService distribuidorDeCargasService;

    @Mock
    private GPS_Tool gpsTool;

    @Mock
    private NotificacionService notificacionService;

    @InjectMocks
    private ServicioLogisticaImpl servicioLogistica;

    private Camion camion;
    private Bulto bulto;
    private Ruta ruta;

    @BeforeEach
    void setUp() {
        camion = new Camion("ABC123", 5000, 30, 3.5);
        bulto = new Bulto(1L, 200, 2.5, 1.2);
        ruta = new Ruta();
        ruta.setDestinos(List.of("Av. Corrientes 1234", "Av. Santa Fe 2500"));
    }
