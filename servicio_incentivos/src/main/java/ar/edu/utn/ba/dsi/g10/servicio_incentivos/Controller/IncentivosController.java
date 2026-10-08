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
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.DTO.CrearPerfilRequest;

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

    //todo: revisar el post donaciones dto

    // POST: Recibe una nueva donación desde el servicio de donaciones usando DonacionDTO
    /*@PostMapping("/{donanteId}/donaciones")
    public ResponseEntity<String> procesarDonacion(
            @PathVariable Long donanteId,
            @RequestBody DonacionDTO donacionDTO) {

        servicio.procesarNuevaDonacionDTO(donanteId, donacionDTO);
        return ResponseEntity.ok("Donación procesada correctamente");
    }*/

    
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

    //todo: revisar estos endpoint


    /*
    // POST: Crear un nuevo perfil de donante conectado desde el servicio de donaciones
    @PostMapping("/perfiles")
    public ResponseEntity<String> crearPerfil(@RequestBody PerfilDonante perfil) {
        servicio.guardarPerfil(perfil);
        return ResponseEntity.status(HttpStatus.CREATED).body("Perfil creado correctamente");
    }
*/

    //todo: revisar como funciona con donador dto

    // POST: Crear perfil de donante desde Servicio de Donaciones enviando DonanteDTO
    /*@PostMapping("/perfiles")
    public ResponseEntity<String> crearPerfil(@RequestBody DonanteDTO donanteDTO) {
        if (donanteDTO == null || donanteDTO.getId() == null || donanteDTO.getId() <= 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("El ID del donante es obligatorio y debe ser un valor positivo");
        }

        if (servicio.getMetricas(donanteDTO.getId()) != null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("El donante ya posee un perfil de incentivos registrado");
        }

        servicio.crearPerfilDesdeDTO(donanteDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Perfil de incentivos creado correctamente");
    }*/

    // crear perfil propuesto
    @PostMapping("/perfiles")
    public ResponseEntity<String> crearPerfil(
        @RequestBody CrearPerfilRequest datos
    ) {
        Long donanteID = datos.donanteID();

        if (donanteID == null || donanteID <= 0) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("donanteID es obligatorio y debe ser positivo");
        }

        if (servicio.getMetricas(donanteID) != null) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("El donante ya tiene un perfil de incentivos");
        }

        servicio.crearPerfil(donanteID);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("Perfil creado correctamente");
    }

    //todo: revisar como funciona con donador dto

    // PUT: Actualizar o sincronizar el perfil de un donante existente
    /*@PutMapping("/perfiles/{donanteId}")
    public ResponseEntity<String> actualizarPerfil(
            @PathVariable Long donanteId,
            @RequestBody DonanteDTO donanteDTO) {

        PerfilDonante existente = servicio.getMetricas(donanteId);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil no encontrado");
        }

        if (donanteDTO != null && donanteDTO.getDonaciones() != null) {
            for (DonacionDTO donacion : donanteDTO.getDonaciones()) {
                servicio.procesarNuevaDonacionDTO(donanteId, donacion);
            }
        }

        return ResponseEntity.ok("Perfil actualizado correctamente");
    }*/


    // PUT: Actualizar un perfil existente buscando por ID
    @PutMapping("/perfiles/{donanteId}")
    public ResponseEntity<String> actualizarPerfil(
            @PathVariable Long donanteId,
            @RequestBody PerfilDonante perfilActualizado) {

        PerfilDonante existente = servicio.getMetricas(donanteId);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil no encontrado");
        }

        perfilActualizado.setDonanteID(donanteId); // Asegura que conserve el ID de la URL
        servicio.guardarPerfil(perfilActualizado);

        return ResponseEntity.ok("Perfil actualizado correctamente");
    }

    // DELETE: Eliminar un perfil de donante por ID
    @DeleteMapping("/perfiles/{donanteId}")
    public ResponseEntity<String> eliminarPerfil(@PathVariable Long donanteId) {
        boolean eliminado = servicio.eliminarPerfil(donanteId);
        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil no encontrado");
        }
        return ResponseEntity.ok("Perfil eliminado correctamente");
    }

}

