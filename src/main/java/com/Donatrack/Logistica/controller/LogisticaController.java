package com.Donatrack.Logistica.controller;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Donatrack.Logistica.domain.Camion;
import com.Donatrack.Logistica.domain.Entrega;
import com.Donatrack.Logistica.domain.EstadoEntrega;
import com.Donatrack.Logistica.domain.LoteRuta;
import com.Donatrack.Logistica.domain.PlanRuta;
import com.Donatrack.Logistica.dto.DonacionAsignadaDTO;
import com.Donatrack.Logistica.dto.ResultadoPlanificacionDTO;
import com.Donatrack.Logistica.dto.SeguimientoDTO;
import com.Donatrack.Logistica.repository.EntregaRepository;
import com.Donatrack.Logistica.service.CamionService;
import com.Donatrack.Logistica.service.DistribucionService;
import com.Donatrack.Logistica.service.EntregaService;
import com.Donatrack.Logistica.service.PlanificacionService;
import com.Donatrack.Logistica.service.RutaService;
import com.Donatrack.Logistica.service.SeguimientoService;

@RestController
@RequestMapping("/api")
public class LogisticaController {
    private final EntregaService entregaService;
    private final PlanificacionService planificacion;
    private final RutaService rutaService;
    private final CamionService camionService;
    private final SeguimientoService seguimiento;
    private final DistribucionService distribucion;
    private final EntregaRepository entregaRepo;

    public LogisticaController(EntregaService entregaService, PlanificacionService planificacion,
                               RutaService rutaService, CamionService camionService,
                               SeguimientoService seguimiento, DistribucionService distribucion,
                               EntregaRepository entregaRepo) {
        this.entregaService = entregaService;
        this.planificacion = planificacion;
        this.rutaService = rutaService;
        this.camionService = camionService;
        this.seguimiento = seguimiento;
        this.distribucion = distribucion;
        this.entregaRepo = entregaRepo;
    }

    // ---- entrada desde el broker ----
    @PostMapping("/entregas/asignadas")
    public ResponseEntity<Entrega> recibirAsignada(@RequestBody DonacionAsignadaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entregaService.registrar(dto));
    }

    @PostMapping("/entregas/asignadas/lote")
    public ResponseEntity<List<Entrega>> recibirAsignadas(@RequestBody List<DonacionAsignadaDTO> dtos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entregaService.registrarTodas(dtos));
    }

    // ---- planificación ----
    @PostMapping("/planificacion/ejecutar")
    public List<LoteRuta> ejecutar(@RequestParam(required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return planificacion.planificarJornada(fecha != null ? fecha : LocalDate.now().plusDays(1));
    }

    @PostMapping("/planificacion/callback")
    public ResponseEntity<Void> callback(@RequestBody ResultadoPlanificacionDTO resultado) {
        planificacion.registrarResultado(resultado);
        return ResponseEntity.ok().build();
    }

    // ---- distribución por capacidad (propuesta, no modifica nada) ----
    @GetMapping("/distribucion/simular")
    public Map<String, Object> simular() {
        var r = distribucion.distribuir(camionService.listarDisponibles(),
                entregaRepo.findByEstadoAndPlanRutaIdIsNull(EstadoEntrega.PENDIENTE));
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("asignadas", r.porCamion().entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue().stream().map(Entrega::getIdDonacion).toList(),
                (a, b) -> a, LinkedHashMap::new)));
        out.put("sinAsignar", r.sinAsignar().stream().map(Entrega::getIdDonacion).toList());
        return out;
    }

    // ---- rutas y entregas ----
    @GetMapping("/rutas")
    public List<PlanRuta> rutas(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return rutaService.rutasDelDia(fecha);
    }

    @PostMapping("/rutas/{id}/iniciar")
    public PlanRuta iniciar(@PathVariable String id) { return rutaService.iniciarRuta(id); }

    @PostMapping("/entregas/{id}/confirmar")
    public Entrega confirmar(@PathVariable String id) { return rutaService.confirmarRecepcion(id); }

    public record FotosRequest(List<String> fotos) {}

    @PostMapping("/entregas/{id}/fotos")
    public Entrega fotos(@PathVariable String id, @RequestBody FotosRequest r) {
        return rutaService.agregarFotos(id, r.fotos());
    }

    public record NoRecibidaRequest(String motivo) {}

    @PostMapping("/entregas/{id}/no-recibida")
    public Entrega noRecibida(@PathVariable String id, @RequestBody NoRecibidaRequest r) {
        return rutaService.informarNoRecibida(id, r.motivo());
    }

    @PostMapping("/entregas/{id}/reponer")
    public Entrega reponer(@PathVariable String id) { return rutaService.reponerEnDeposito(id); }

    // ---- camiones ----
    public record CamionRequest(String patente, double capacidadCarga, double capacidadVolumenM3, double alturaM) {}

    @PostMapping("/camiones")
    public ResponseEntity<Camion> crearCamion(@RequestBody CamionRequest r) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(camionService.crear(r.patente(), r.capacidadCarga(), r.capacidadVolumenM3(), r.alturaM()));
    }

    @GetMapping("/camiones")
    public List<Camion> camiones() { return camionService.listar(); }

    public record UbicacionRequest(double latitud, double longitud) {}

    @PutMapping("/camiones/{patente}/ubicacion")
    public Camion ubicacion(@PathVariable String patente, @RequestBody UbicacionRequest r) {
        return camionService.actualizarUbicacion(patente, r.latitud(), r.longitud());
    }

    // ---- seguimiento (mapas de donantes y entidades) ----
    @GetMapping("/seguimiento/donantes/{id}")
    public List<SeguimientoDTO> porDonante(@PathVariable Long id) { return seguimiento.porDonante(id); }

    @GetMapping("/seguimiento/entidades/{id}")
    public List<SeguimientoDTO> porEntidad(@PathVariable Long id) { return seguimiento.porEntidad(id); }

    public record DisponibleRequest(boolean disponible) {}

    @PutMapping("/camiones/{patente}/disponible")
    public Camion disponible(@PathVariable String patente, @RequestBody DisponibleRequest r) {
        return camionService.setDisponible(patente, r.disponible());
    }

    // ---- entregas en traslado de una entidad (para el front) ----
    @GetMapping("/entidades/{id}/entregas")
    public List<Entrega> entregasDeEntidad(@PathVariable Long id) {
        return entregaRepo.findByIdEntidadAndEstado(id, EstadoEntrega.EN_TRASLADO);
    }
}