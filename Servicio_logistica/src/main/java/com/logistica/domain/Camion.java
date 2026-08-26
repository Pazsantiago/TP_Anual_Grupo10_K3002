public class Camion {
    private String patente;
    private double capacidadCarga;
    private double capacidadVolumen;
    private double altura;

    @OneToMany(mappedBy = "camion")
    private List<Bulto> bultosAsignados;
}
