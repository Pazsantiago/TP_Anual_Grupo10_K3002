public class RutaResponse {
    private Camion camion;
    private List<String> destinos;
    private List<Entrega> entregas;
}

    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    public List<String> getDestinos() {
        return destinos;
    }

    public void setDestinos(List<String> destinos) {
        this.destinos = destinos;
    }

    public List<Entrega> getEntregas() {
        return entregas;
    }

    public void setEntregas(List<Entrega> entregas) {
        this.entregas = entregas;
    }

    public RutaResponse(Camion camion, List<String> destinos, List<Entrega> entregas) {
        this.camion = camion;
        this.destinos = destinos;
        this.entregas = entregas;
    }


