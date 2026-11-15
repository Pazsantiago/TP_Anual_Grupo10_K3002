package com.grupo10.servicio_donaciones.Controllers.cDonaciones;

import com.grupo10.servicio_donaciones.Services.ServiceDonaciones.ServicioDonacion;
import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.Donacion;
import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.DonacionSegmentada;
import com.grupo10.servicio_donaciones.donaciones.dominio.donacion.TipoEstadoDonacion;
import com.grupo10.servicio_donaciones.dtos.request.DonacionRequestDTO;
import com.grupo10.servicio_donaciones.dtos.request.EstadoDonacionRequestDTO;
import com.grupo10.servicio_donaciones.dtos.response.DonacionResponseDTO;
import com.grupo10.servicio_donaciones.dtos.response.DonacionSegmentadaResponseDTO;
import com.grupo10.servicio_donaciones.mapper.DTOSMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/donantes/{idDonante}/donaciones")
@RequiredArgsConstructor
public class CDonacion {

    private final DTOSMapper mapper;
    private final ServicioDonacion servicioDonacion;

    // Obtener todas las Donaciones
    @GetMapping("")
    public ResponseEntity<List<DonacionResponseDTO>> getAllDonaciones(@PathVariable Long idDonante) {
        try {
            return ResponseEntity.ok(
                    servicioDonacion.getAllDonaciones(idDonante).stream()
                            .map(mapper::deDominioAResponse)
                            .toList()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener una Donacion por idDonacion
    @GetMapping("/{idDonacion}")
    public ResponseEntity<DonacionResponseDTO> getDonacionById(@PathVariable Long idDonacion) {
        try {
            return ResponseEntity.ok(mapper.deDominioAResponse(servicioDonacion.getDonacionById(idDonacion)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener todas las donaciones segmentadas
    @GetMapping("/segmentadas")
    public ResponseEntity<List<DonacionSegmentadaResponseDTO>> obtenerDonacionesSegmentadasPorIdDonante(
            @PathVariable Long idDonante) {
        try {
            return ResponseEntity.ok(servicioDonacion.obtenerDonacionesSegmentadas(idDonante).stream().map(mapper::deDominioAResponse).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener todas las donaciones segmentadas por estado
    @GetMapping("/{idDonacion}/segmentadas/estado")
    public ResponseEntity<List<DonacionSegmentada>> obtenerDonacionesSegmentadasPorEstado(
            @PathVariable Long idDonante,
            @RequestParam(value = "estadoDonacion") TipoEstadoDonacion estadoDonacion) {
        try {
            return ResponseEntity.ok(servicioDonacion.obtenerDonacionesSegmentadasPorEstado(idDonante, estadoDonacion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener todas las donaciones segmentadas por subcategoria
    @GetMapping("/{idDonacion}/segmentadas/subcategoria")
    public ResponseEntity<List<DonacionSegmentada>> obtenerDonacionesSegmentadasPorSubcategoria(
            @PathVariable Long idDonante,
            @RequestParam(value = "subcategoria") String subcategoria) {
        try {
            return ResponseEntity.ok(servicioDonacion.obtenerDonacionesSegmentadasPorSubcategoria(idDonante, subcategoria));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener todas las donaciones segmentadas por categoria
    @GetMapping("/{idDonacion}/segmentadas/categoria")
    public ResponseEntity<List<DonacionSegmentada>> obtenerDonacionesSegmentadasPorCategoria(
            @PathVariable Long idDonante,
            @PathVariable Long idCategoria) {
        try {
            return ResponseEntity.ok(servicioDonacion.obtenerDonacionesSegmentadasPorCategoria(idDonante, idCategoria));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Obtener una donacion segmentada especifica
    @GetMapping("/{idDonacion}/segmentadas/{idSegmentada}")
    public ResponseEntity<DonacionSegmentada> obtenerDonacionSegmentada(
            @PathVariable Long idDonante,
            @PathVariable Long idDonacion,
            @PathVariable Long idSegmentada) {
        try {
            return ResponseEntity.ok(servicioDonacion.obtenerDonacionSegmentada(idSegmentada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Agregar una nueva Donacion
    @PostMapping("")
    public ResponseEntity<DonacionResponseDTO> createDonacion(
            @RequestBody DonacionRequestDTO dto,
            @PathVariable Long idDonante) {
        try {
            Donacion donacion = mapper.deDTORequestADominio(dto);
            Donacion creada = servicioDonacion.createDonacion(donacion, idDonante);
            return ResponseEntity.ok(mapper.deDominioAResponse(creada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Actualizar una Donacion existente
    @PutMapping("/{idDonacion}")
    public ResponseEntity<DonacionResponseDTO> updateDonacion(
            @RequestBody DonacionRequestDTO dto,
            @PathVariable Long idDonacion) {
        try {
            Donacion donacion = mapper.deDTORequestADominio(dto);
            Donacion actualizada = servicioDonacion.updateDonacion(idDonacion, donacion);
            return ResponseEntity.ok(mapper.deDominioAResponse(actualizada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Eliminar una Donacion
    @DeleteMapping("/{idDonacion}")
    public ResponseEntity<String> deleteDonacion(
            @PathVariable Long idDonante,
            @PathVariable Long idDonacion) {
        try {
            return ResponseEntity.ok(servicioDonacion.deleteDonacion(idDonacion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Indicar que una Donacion esta vencida
    @PatchMapping("/{idDonacion}/segmentadas/{idSegmentada}")
    public ResponseEntity<String> actualizarEstadoDonacionAVencida(
            @PathVariable Long idDonacion,
            @PathVariable Long idSegmentada,
            @RequestBody EstadoDonacionRequestDTO dto) {
        try {
            return ResponseEntity.ok(servicioDonacion.actualizarDonacionAVencida(idDonacion, idSegmentada, mapper.deDTORequestADominio(dto)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}