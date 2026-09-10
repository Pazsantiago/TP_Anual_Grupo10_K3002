package com.Donatrack.Logistica.Rutas.service.serviceImpl;
import com.Donatrack.Logistica.Rutas.domain.Ruta;
import com.Donatrack.Logistica.Rutas.domain.RutaResponse;
import com.Donatrack.Logistica.Rutas.service.GPSService;
import com.Donatrack.Logistica.domain.Direccion;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.Donatrack.Logistica.Rutas.domain.GpsTool;
import com.Donatrack.Logistica.Rutas.external.GPS_Tool;
import java.util.ArrayList;
import java.util.List;

@Service
public class GPSServiceImpl implements GPSService {

    private static final String GOOGLE_MAPS_API_URL = "https://maps.googleapis.com/maps/api/directions/json";
    private final RestTemplate restTemplate;
    private final String apiKey = "TU_API_KEY_AQUI"; // ⚠️ reemplazar con tu API Key real

    public GPSServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public void guardar(RutaResponse rutaResponse){
        // Convertimos el DTO en un objeto de dominio Ruta
        Ruta ruta = new Ruta();
        ruta.setDestinos(rutaResponse.getDestinos());
        ruta.setPasos(rutaResponse.getPasos());
        ruta.setEstado(Ruta.EstadoRuta.REGISTRADA);

        // Aquí podrías persistir la ruta en la base de datos
        // por ejemplo usando un repositorio JPA:
        // rutaRepository.save(ruta);

        System.out.println("Ruta registrada: " + ruta);
    }
    @Override
    public Ruta planificarRuta(List<Direccion> direcciones) {
        try {
            String origen = direcciones.get(0).toString();
            String destino = direcciones.get(direcciones.size() - 1).toString();
            String waypoints = direcciones.subList(1, direcciones.size() - 1)
                    .stream()
                    .map(Direccion::toString)
                    .reduce((a, b) -> a + "|" + b)
                    .orElse("");

            String url = String.format("%s?origin=%s&destination=%s&waypoints=%s&key=%s",
                    GOOGLE_MAPS_API_URL, origen, destino, waypoints, apiKey);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            Ruta ruta = new Ruta();
            List<String> pasos = new ArrayList<>();

            root.path("routes").get(0).path("legs").forEach(leg -> {
                leg.path("steps").forEach(step -> {
                    pasos.add(step.path("html_instructions").asText());
                });
            });

            ruta.setDestinos(direcciones);
            ruta.setPasos(pasos);
            ruta.setEstado(Ruta.EstadoRuta.PLANIFICADA);

            return ruta;
        } catch (Exception e) {
            e.printStackTrace();
            // En caso de error, devolvemos una ruta mock para no romper el flujo
            Ruta rutaMock = new Ruta();
            rutaMock.setDestinos(direcciones);
            rutaMock.setPasos(List.of("Mock: salida", "Mock: llegada"));
            rutaMock.setEstado(Ruta.EstadoRuta.PLANIFICADA);//"Planificada (mock)"
            return rutaMock;
        }
    }
}