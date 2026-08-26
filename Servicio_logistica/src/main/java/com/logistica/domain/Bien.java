public class Bien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int cantidad;
    private String tipo;

    @ManyToOne
    private Bulto bulto;
}
