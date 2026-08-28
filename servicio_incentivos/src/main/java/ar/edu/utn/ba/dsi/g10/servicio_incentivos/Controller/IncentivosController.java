package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Controller;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Insignia;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.ProgresoMision;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Ranking.PosicionRanking;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.ServicioIncentivos;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.MisionResponseDTO;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.PosicionRankingDTO;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.PerfilMetricasDTO;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.InsigniaResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Collections;
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
    @PostMapping("/{donanteId}/donaciones")
    public ResponseEntity<String> procesarDonacion(
            @PathVariable Long donanteId,
            @RequestBody DonacionImportada donacion) {
        servicio.procesarNuevaDonacion(donanteId, donacion);
        return ResponseEntity.ok("Donación procesada correctamente");
    }

    
    // get funcionando, Se instancio una mision racha y al donar este iba aumentando el progreso.
    @GetMapping("/{donanteId}/misiones")
    public ResponseEntity<?> obtenerMisionActual(@PathVariable Long donanteId) {
   

  

        // cambios para que se pueda ver el progreso de la mision
        PerfilDonante perfil = servicio.getMetricas(donanteId);

        if (perfil == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil no encontrado");
        }

        Mision mision = perfil.getMisionActual();
        if (mision == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Misión no encontrada");
        }

        ProgresoMision progresoMision = perfil.getProgreso();

        List<DonacionImportada> historial = (progresoMision != null && progresoMision.getHistorialDonaciones() != null)
                ? progresoMision.getHistorialDonaciones()
                : Collections.emptyList();

        MisionResponseDTO dto = new MisionResponseDTO(mision, historial);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/{donanteId}/insignias")
    public ResponseEntity<?> obtenerInsignias(@PathVariable Long donanteId) {
        List<Insignia> insignias = servicio.getInsignias(donanteId);
    
         if (insignias == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Insignias o perfil no encontrado");
            }
        

        List<InsigniaResponseDTO> listaDto = insignias.stream()
                .map(InsigniaResponseDTO::new)
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
        return ResponseEntity.ok(new PerfilMetricasDTO(perfilDonante));
    }
  @GetMapping("/ranking-mensual")
public ResponseEntity<?> obtenerRankingMensual(){
    
    List<PosicionRanking> ranking = servicio.getRanking(10); 

    
    List<PosicionRankingDTO> rankingDTO = ranking.stream()
                .map(PosicionRankingDTO::new)
                .toList();


    System.out.println("DEBUG - Elementos en ranking original: " + ranking.size());
    System.out.println("DEBUG - Elementos en ranking dto: " + rankingDTO.size());
    return ResponseEntity.ok(rankingDTO);
}

    // hardcodeado para probar la donacion 5
    @PostMapping("/{donanteId}/nueva-mision")
    public ResponseEntity<String> asignarMision(
            @PathVariable Long donanteId) {

        // Creamos una misión genérica rápida para la prueba
        ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.MisionCompletitud nuevaMision = new ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.MisionCompletitud();
        nuevaMision.setId(99L);
        nuevaMision.setNombre("Misión de Refuerzo");
        nuevaMision.setCategoriasDistintasRequeridas(1.0);

        servicio.asignarNuevaMision(donanteId, nuevaMision);

        return ResponseEntity.ok("Nueva misión asignada correctamente");
    }

}

