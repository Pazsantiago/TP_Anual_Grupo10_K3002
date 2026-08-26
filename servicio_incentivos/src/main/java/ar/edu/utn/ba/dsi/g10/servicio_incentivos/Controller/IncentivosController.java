package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Controller;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Insignia;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.ServicioIncentivos;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;


//PENDIENTE: Manejo de errores


@RestController
@RequestMapping("/incentivos")
public class IncentivosController {
    private final ServicioIncentivos servicio;
    /* 
    public IncentivosController() {
        this(new ServicioIncentivos());
    }
    */
    public IncentivosController(ServicioIncentivos servicio) {
        this.servicio = servicio;
    }
    //post funcionando
    @PostMapping("/donaciones")
    public ResponseEntity<String> procesarDonacion(
            @RequestParam Long donanteId,
            @RequestBody DonacionImportada donacion) {
        servicio.procesarNuevaDonacion(donanteId, donacion);
        return ResponseEntity.ok("Donación procesada correctamente");
    }

    //revisar logica para diferenciar donaciones entregas o no
    @PostMapping("/donaciones-entregadas")
    public ResponseEntity<String> procesarDonacionEntregada(
            @RequestParam Long donanteId,
            @RequestBody DonacionImportada donacion) {
        servicio.procesarDonacionEntregada(donanteId, donacion);
        return ResponseEntity.ok("Donación entregada procesada correctamente");
    }
    // test pendiente
    @GetMapping("/{donanteId}/mision")
    public ResponseEntity<?> obtenerMisionActual(@PathVariable Long donanteId) {
        Mision mision = servicio.getMisionActual(donanteId);

    if (mision == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mision o perfil no encontrado");
    }
        Mapper dto = new Mapper(mision);
        return ResponseEntity.ok(dto);
    }
    //test pendiente
    @GetMapping("/{donanteId}/insignias")
    public ResponseEntity<?> obtenerInsignias(@PathVariable Long donanteId) {
        List<Insignia> insignias = servicio.getInsignias(donanteId);
    
         if (insignias == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Insignias o perfil no encontrado");
            }
        

        Mapper dto = new Mapper();
        
        List<Mapper> listaDto = insignias.stream()
            .map(insignia -> dto.transformarInsignia(insignia))
            .toList();

        return ResponseEntity.ok(listaDto);
    }
    //test exitoso con perfil de test en repo
    @GetMapping("/{donanteId}/metricas")
    public ResponseEntity<?> obtenerMetricas(@PathVariable Long donanteId) {
        PerfilDonante perfilDonante = servicio.getMetricas(donanteId);

       
        if (perfilDonante == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil no encontrado");
    }
        Mapper dto = new Mapper(perfilDonante);
        return ResponseEntity.ok(dto);
    }
}
