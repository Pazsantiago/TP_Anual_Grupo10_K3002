public class Camion {
    private String patente;
    private double capacidadCarga;
    private double capacidadVolumen;

    public Camion(String patente, double capacidadCarga, double capacidadVolumen, double altura, List<Bulto> bultosAsignados) {
        this.patente = patente;
        this.capacidadCarga = capacidadCarga;
        this.capacidadVolumen = capacidadVolumen;
        this.altura = altura;
        this.bultosAsignados = bultosAsignados;
    }

    private double altura;

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public double getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(double capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public double getCapacidadVolumen() {
        return capacidadVolumen;
    }

    public void setCapacidadVolumen(double capacidadVolumen) {
        this.capacidadVolumen = capacidadVolumen;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public List<Bulto> getBultosAsignados() {
        return bultosAsignados;
    }

    public void setBultosAsignados(List<Bulto> bultosAsignados) {
        this.bultosAsignados = bultosAsignados;
    }

    @OneToMany(mappedBy = "camion")
    private List<Bulto> bultosAsignados;
}
