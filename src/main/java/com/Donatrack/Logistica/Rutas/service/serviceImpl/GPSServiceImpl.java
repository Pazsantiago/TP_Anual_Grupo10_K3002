package com.Donatrack.Logistica.Rutas.service.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
/*import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;*/
import com.Donatrack.Logistica.Rutas.external.GPS_Tool;
import com.Donatrack.Logistica.Rutas.domain.Ruta;
import com.Donatrack.Logistica.Rutas.service.GPSService;
import com.Donatrack.Logistica.domain.Direccion;
import java.util.List;


@Service
public class GPSServiceImpl implements GPSService {

    private final GPS_Tool gpsTool;

    public GPSServiceImpl(GPS_Tool gpsTool) {
        this.gpsTool = gpsTool;
    }

    @Override
    public Ruta generarRuta(List<String> direcciones) {
        List<Direccion> destinos = direcciones.stream()
                .map(direccion -> new Direccion(direccion, ""))
                .toList();
        return gpsTool.planificarRuta(destinos);
    }
}
