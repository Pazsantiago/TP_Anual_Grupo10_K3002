public class Ruta {
    public Ruta(List<Destino> destinos) {
        this.destinos = destinos;
    }

    public List<Destino> getDestinos() {
        return destinos;
    }

    public void setDestinos(List<Destino> destinos) {
        this.destinos = destinos;
    }

    private List<Destino> destinos = new ArrayList<>();
}

