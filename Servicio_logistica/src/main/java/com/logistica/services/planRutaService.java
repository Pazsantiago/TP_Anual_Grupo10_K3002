@Service
public class PlanRutaService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    public PlanRuta generarPlanRuta(List<Direccion> direcciones) {
    // Construir la URL para la API de Google Directions
    String origen = direcciones.get(0).getLatitud() + "," + direcciones.get(0).getLongitud();
    String destino = direcciones.get(direcciones.size() - 1).getLatitud() + "," + direcciones.get(direcciones.size() - 1).getLongitud();

    String waypoints = direcciones.subList(1, direcciones.size() - 1)
        .stream()
        .map(d -> d.getLatitud() + "," + d.getLongitud())
        .collect(Collectors.joining("|"));

    String url = String.format(
        "https://maps.googleapis.com/maps/api/directions/json?origin=%s&destination=%s&waypoints=%s&key=%s",
        origen, destino, waypoints, apiKey
    );

    // Llamada HTTP a la API de Google Maps
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    // Parsear la respuesta JSON
    JSONObject json = new JSONObject(response.getBody());
    JSONObject route = json.getJSONArray("routes").getJSONObject(0);
    JSONObject leg = route.getJSONArray("legs").getJSONObject(0);

    PlanRuta planRuta = new PlanRuta();
    planRuta.setDistanciaTotal(leg.getJSONObject("distance").getDouble("value"));
    planRuta.setTiempoEstimado(leg.getJSONObject("duration").getString("text"));
    planRuta.setDirecciones(direcciones);

    return planRuta;
}
}
