package com.grupo10.servicio_donaciones.Controllers.cCategorias;


import com.grupo10.servicio_donaciones.repositorios.RepoCategorias;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CCategorias {

    private final RepoCategorias repoCategorias;

    @GetMapping("")
    public ResponseEntity<?> getAllEntidades() {
        try {
            return ResponseEntity.ok(repoCategorias.findAll().stream().toList());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}