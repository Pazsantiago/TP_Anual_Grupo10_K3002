package com.Donatrack.Logistica.controller;

import com.Donatrack.Logistica.domain.Bulto;
import com.Donatrack.Logistica.services.BultoService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@RestController
@RequestMapping("/api/bultos")
public class BultoController {

    private final BultoService bultoService;

    public BultoController(BultoService bultoService) {
        this.bultoService = bultoService;
    }

    // Listar todos los bultos pendientes
    @GetMapping
    public List<Bulto> listarBultos() {
        return bultoService.obtenerBultosPendientes();
    }

    // Registrar un nuevo bulto
    @PostMapping
    public Bulto registrarBulto(@RequestBody Bulto bulto) {
        return bultoService.guardar(bulto);
    }
}
