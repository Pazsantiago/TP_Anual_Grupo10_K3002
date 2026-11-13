package com.Donatrack.Logistica.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Donatrack.Logistica.domain.Camion;
import com.Donatrack.Logistica.domain.Direccion;
import com.Donatrack.Logistica.domain.Entrega;
import com.Donatrack.Logistica.domain.EstadoEntrega;
import com.Donatrack.Logistica.domain.EstadoPlan;
import com.Donatrack.Logistica.domain.PlanRuta;
import com.Donatrack.Logistica.repository.CamionRepository;
import com.Donatrack.Logistica.repository.EntregaRepository;
import com.Donatrack.Logistica.repository.PlanRutaRepository;
import com.Donatrack.Logistica.service.RutaService;

/** Adaptador de compatibilidad con el cliente liviano (rama_cliente). Reutiliza los mismos servicios. */
@RestController
@RequestMapping("/api")
public class ClienteCompatController {
    private final EntregaRepository entregaRepo;
    private final CamionRepository camionRepo;
    private final PlanRutaRepository planRepo;
    private final RutaService rutaService;

    public ClienteCompatController(EntregaRepository entregaRepo, CamionRepository camionRepo,
                                   PlanRutaRepository planRepo, RutaService rutaService) {
        this.entregaRepo = entregaRepo;
        this.camionRepo = camionRepo;
        this.planRepo = planRepo;
        this.rutaService = rutaService;
    }

    // ---------- camiones ----------
    public record CamionBody(String patente, Double capacidadCarga, Double capacidadVolumenM3,
                             Double alturaM, Boolean disponible) {}

    @PutMapping("/camiones/{id}")
    public Camion actualizar(@PathVariable String id, @RequestBody CamionBody b) {
        Camion c = camionRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Camión no encontrado: " + id));
        if (b.patente() != null && !b.patente().isBlank()) c.setPatente(b.patente().trim().toUpperCase());
        if (b.capacidadCarga() != null && b.capacidadCarga() > 0) c.setCapacidadCarga(b.capacidadCarga());
        if (b.capacidadVolumenM3() != null && b.capacidadVolumenM3() > 0) c.setCapacidadVolumenM3(b.capacidadVolumenM3());
        if (b.alturaM() != null && b.alturaM() > 0) c.setAlturaM(b.alturaM());
        if (b.disponible() != null) c.setDisponible(b.disponible());
        return camionRepo.save(c);
    }

    /** Baja lógica: el camión queda no disponible. */
    @DeleteMapping("/camiones/{id}")
    public ResponseEntity<Void> baja(@PathVariable String id) {
        Camion c = camionRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Camión no encontrado: " + id));
        c.setDisponible(false);
        camionRepo.save(c);
        return ResponseEntity.noContent().build();
    }

    // ---------- donaciones (vista del cliente sobre las entregas) ----------
    public record DireccionView(String calle, String ciudad) {}
    public record BultoView(String id, double peso, double volumen, double altura) {}
    public record DonacionView(String idDonacion, String destinatario, String estadoDonacion,
                               DireccionView direccion, List<BultoView> bultos) {}

    private DonacionView vista(Entrega e) {
        Direccion d = e.getDireccion();
        return new DonacionView(
                String.valueOf(e.getIdDonacion()),
                e.getIdEntidad() == null ? null : "Entidad " + e.getIdEntidad(),
                e.getEstado().name(),
                d == null ? null : new DireccionView(d.getCalle(), d.getCiudad()),
                e.getBultos().stream()
                        .map(b -> new BultoView(b.getId(), b.getPeso(), b.getVolumen(), b.getAltura()))
                        .toList());
    }

    /** Entregas no finalizadas (el cliente filtra por estado). */
    @GetMapping("/donaciones/pendientes")
    public List<DonacionView> donaciones() {
        return entregaRepo.findAll().stream()
                .filter(e -> e.getEstado() != EstadoEntrega.ENTREGADA)
                .map(this::vista)
                .toList();
    }

    @GetMapping("/bultos/pendientes")
    public List<BultoView> bultos() {
        return entregaRepo.findByEstadoAndPlanRutaIdIsNull(EstadoEntrega.PENDIENTE).stream()
                .flatMap(e -> e.getBultos().stream())
                .map(b -> new BultoView(b.getId(), b.getPeso(), b.getVolumen(), b.getAltura()))
                .toList();
    }

    // ---------- ruta ----------
    public record IniciarRutaBody(String idCamion) {}

    /** Inicia la ruta PLANIFICADA más próxima del camión indicado (por id). */
    @PostMapping("/rutas/iniciar")
    public PlanRuta iniciar(@RequestBody IniciarRutaBody b) {
        PlanRuta plan = planRepo.findByEstado(EstadoPlan.PLANIFICADA).stream()
                .filter(p -> b.idCamion() != null && b.idCamion().equals(p.getIdCamion()))
                .min(Comparator.comparing(PlanRuta::getFecha))
                .orElseThrow(() -> new NoSuchElementException("El camión no tiene una ruta planificada"));
        return rutaService.iniciarRuta(plan.getId());
    }

    // ---------- recepción y devolución ----------
    @PostMapping(value = "/donaciones/{id}/recepcion", consumes = "multipart/form-data")
    public ResponseEntity<Void> recepcion(@PathVariable Long id,
                                          @RequestParam String resultado,
                                          @RequestParam(required = false) String observaciones,
                                          @RequestParam(value = "fotos", required = false) List<MultipartFile> fotos) {
        Entrega e = entregaRepo.findByIdDonacion(id)
                .orElseThrow(() -> new NoSuchElementException("Donación no encontrada: " + id));

        if ("RECIBIDA".equalsIgnoreCase(resultado)) {
            rutaService.confirmarRecepcion(e.getId());
            List<String> nombres = new ArrayList<>();
            if (fotos != null) {
                for (MultipartFile f : fotos) {
                    if (!f.isEmpty()) nombres.add(f.getOriginalFilename());
                }
            }
            if (!nombres.isEmpty()) rutaService.agregarFotos(e.getId(), nombres);
        } else if ("NO_RECIBIDA".equalsIgnoreCase(resultado)) {
            rutaService.informarNoRecibida(e.getId(),
                    observaciones == null || observaciones.isBlank() ? "No recibida" : observaciones);
        } else {
            throw new IllegalArgumentException("resultado debe ser RECIBIDA o NO_RECIBIDA");
        }
        return ResponseEntity.ok().build();
    }

    public record DevolucionBody(String justificacion) {}

    @PostMapping("/donaciones/{id}/devolucion")
    public ResponseEntity<Void> devolucion(@PathVariable Long id, @RequestBody DevolucionBody b) {
        Entrega e = entregaRepo.findByIdDonacion(id)
                .orElseThrow(() -> new NoSuchElementException("Donación no encontrada: " + id));
        rutaService.reponerEnDeposito(e.getId());
        return ResponseEntity.ok().build();
    }
}