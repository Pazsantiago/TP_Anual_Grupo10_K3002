@Component
public class GPS_Tool {

    private static final String GOOGLE_MAPS_API_URL = "https://maps.googleapis.com/maps/api/directions/json";
    private final RestTemplate restTemplate;
    private final String apiKey = "TU_API_KEY_AQUI"; // clave de Google Maps

    public GPS_Tool(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PlanRuta planificarRuta(List<String> direcciones) {
        String origen = direcciones.get(0);
        String destino = direcciones.get(direcciones.size() - 1);
        String waypoints = String.join("|", direcciones.subList(1, direcciones.size() - 1));

        String url = String.format("%s?origin=%s&destination=%s&waypoints=%s&key=%s",
                GOOGLE_MAPS_API_URL, origen, destino, waypoints, apiKey);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return parsearRuta(response.getBody());
    }

    private PlanRuta parsearRuta(String jsonResponse) {
        JSONObject json = new JSONObject(jsonResponse);
        JSONArray routes = json.getJSONArray("routes");
        JSONObject route = routes.getJSONObject(0);
        JSONArray legs = route.getJSONArray("legs");

        PlanRuta ruta = new PlanRuta();
        for (int i = 0; i < legs.length(); i++) {
            JSONObject leg = legs.getJSONObject(i);
            Direccion destino = new Direccion(
                    leg.getString("end_address"),
                    leg.getJSONObject("distance").getString("text"),
                    leg.getJSONObject("duration").getString("text")
            );
            ruta.getDestinos().add(destino);
        }
        return ruta;
    }
}
