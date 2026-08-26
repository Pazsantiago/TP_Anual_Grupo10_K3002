public interface distribucionDeCargasService {
    Map<Camion, List<Bulto>> asignarBultos(List<Camion> camiones, List<Bulto> bultos);
    boolean validarCapacidad(Camion camion, Bulto bulto);
    double calcularEspacioDisponible(Camion camion);
}
