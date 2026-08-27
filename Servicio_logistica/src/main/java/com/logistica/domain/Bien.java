public class Bien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Bien(Long id, String nombre, int cantidad, String tipo, Bulto bulto) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.bulto = bulto;
    }

    private String nombre;
    private int cantidad;
    private String tipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Bulto getBulto() {
        return bulto;
    }

    public void setBulto(Bulto bulto) {
        this.bulto = bulto;
    }

    @ManyToOne
    private Bulto bulto;
}
