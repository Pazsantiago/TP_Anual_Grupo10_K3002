public interface servicioLogistica {
    void distribuirBultos(List<Camion> camiones, List<Bulto> bultos);
    Ruta planificarRuta(List<String> direcciones);
    void iniciarRuta(Ruta ruta);
    void registrarEntrega(Entrega entrega);
}