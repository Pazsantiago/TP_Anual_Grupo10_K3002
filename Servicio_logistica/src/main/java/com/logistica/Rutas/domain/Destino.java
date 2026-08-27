public class Destino {
    private String direccion;
    private String distancia;
    private String duracion;
}

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public Destino(String direccion, String distancia, String duracion) {
        this.direccion = direccion;
        this.distancia = distancia;
        this.duracion = duracion;
    }

