public class PlanRuta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<Direccion> direcciones;
    private Double distanciaTotal;
    private String tiempoEstimado;
}
