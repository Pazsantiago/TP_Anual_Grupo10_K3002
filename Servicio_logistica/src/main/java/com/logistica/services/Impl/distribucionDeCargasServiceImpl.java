@Service
public class distribucionDeCargasServiceImpl implements DistribucionDeCargasService {

    @Override
    public Map<Camion, List<Bulto>> asignarBultos(List<Camion> camiones, List<Bulto> bultos) {
        Map<Camion, List<Bulto>> asignaciones = new HashMap<>();
        for (Camion camion : camiones) {
            asignaciones.put(camion, new ArrayList<>());
        }

        for (Bulto bulto : bultos) {
            for (Camion camion : camiones) {
                if (validarCapacidad(camion, bulto)) {
                    asignaciones.get(camion).add(bulto);
                    break;
                }
            }
        }
        return asignaciones;
    }

    @Override
    public boolean validarCapacidad(Camion camion, Bulto bulto) {
        double pesoActual = camion.getBultosAsignados().stream().mapToDouble(Bulto::getPeso).sum();
        double volumenActual = camion.getBultosAsignados().stream().mapToDouble(Bulto::getVolumen).sum();
        return (pesoActual + bulto.getPeso() <= camion.getCapacidadCarga()) &&
                (volumenActual + bulto.getVolumen() <= camion.getCapacidadVolumen());
    }

    @Override
    public double calcularEspacioDisponible(Camion camion) {
        double volumenUsado = camion.getBultosAsignados().stream().mapToDouble(Bulto::getVolumen).sum();
        return camion.getCapacidadVolumen() - volumenUsado;
    }
}
