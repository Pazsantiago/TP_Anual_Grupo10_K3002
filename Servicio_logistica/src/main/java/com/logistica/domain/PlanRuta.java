public class PlanRuta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<Direccion> direcciones;

    public PlanRuta(Long id, List<Direccion> direcciones, Double distanciaTotal, String tiempoEstimado) {
        this.id = id;
        this.direcciones = direcciones;
        this.distanciaTotal = distanciaTotal;
        this.tiempoEstimado = tiempoEstimado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    public Double getDistanciaTotal() {
        return distanciaTotal;
    }

    public void setDistanciaTotal(Double distanciaTotal) {
        this.distanciaTotal = distanciaTotal;
    }

    public String getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(String tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    private Double distanciaTotal;
    private String tiempoEstimado;
}
