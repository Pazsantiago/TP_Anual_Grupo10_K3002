@Entity
public class Bulto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double peso;
    private double volumen;
    private double altura;

    @ManyToOne
    private Camion camion;

    @OneToMany(mappedBy = "bulto")
    private List<Bien> bienes;
}
