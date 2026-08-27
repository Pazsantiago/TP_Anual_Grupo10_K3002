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

    /*
    //TODO: revisar logica para diferenciar donaciones entregas o no
    @PostMapping("/donaciones-entregadas")
    public ResponseEntity<String> procesarDonacionEntregada(
            @RequestParam Long donanteId,
            @RequestBody DonacionImportada donacion) {
        servicio.procesarDonacionEntregada(donanteId, donacion);
        return ResponseEntity.ok("Donación entregada procesada correctamente");
    }
    */
    // get funcionando, Se instancio una mision racha y al donar este iba aumentando el progreso.
    @GetMapping("/{donanteId}/misiones")
    public ResponseEntity<?> obtenerMisionActual(@PathVariable Long donanteId) {
        //Mision mision = servicio.getMisionActual(donanteId);

    /*if (mision == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mision o perfil no encontrado");
    }
        Mapper dto = new Mapper(mision);
        return ResponseEntity.ok(dto);*/

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

    //TODO: test pendiente
    @GetMapping("/{donanteId}/insignias")
    public ResponseEntity<?> obtenerInsignias(@PathVariable Long donanteId) {
        List<Insignia> insignias = servicio.getInsignias(donanteId);
    
         if (insignias == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Insignias o perfil no encontrado");
            }
        

        //Mapper dto = new Mapper();
        /*
        List<Mapper> listaDto = insignias.stream()
            .map(insignia -> {Mapper dto = new Mapper();
                dto.transformarInsignia(insignia);
                return dto;})
            .toList();

        return ResponseEntity.ok(listaDto);*/
        List<InsigniaResponseDTO> listaDto = insignias.stream()
                .map(InsigniaResponseDTO::new)
                .toList();

        return ResponseEntity.ok(listaDto);
    }
    //test exitoso con perfil de test en repo
    @GetMapping("/{donanteId}/metricas")
    public ResponseEntity<?> obtenerMetricas(@PathVariable Long donanteId) {
        PerfilDonante perfilDonante = servicio.getMetricas(donanteId);

       /*
        if (perfilDonante == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil no encontrado");
    }
        Mapper dto = new Mapper(perfilDonante);
        return ResponseEntity.ok(dto);
    }*/
        if (perfilDonante == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil no encontrado");
        }
        return ResponseEntity.ok(new PerfilMetricasDTO(perfilDonante));
    }
  @GetMapping("/ranking-mensual")
public ResponseEntity<?> obtenerRankingMensual(){
    // 1. Llamamos al servicio que ejecuta tu proceso de negocio
    List<PosicionRanking> ranking = servicio.getRanking(10); // Por ejemplo, límite de 10 posiciones
    /*if (ranking.isEmpty()) {
        return ResponseEntity.noContent().build();
    }*/
    
    List<PosicionRankingDTO> rankingDTO = ranking.stream()
                .map(PosicionRankingDTO::new)
                .toList();

    // 2. Retornamos la lista directamente con un 200 OK
    System.out.println("DEBUG - Elementos en ranking original: " + ranking.size());
    System.out.println("DEBUG - Elementos en ranking dto: " + rankingDTO.size());
    return ResponseEntity.ok(rankingDTO);
}

}

