package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Prueba;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ControllerPrueba {

    @GetMapping("/hola")
    public String holaMundo(){
        return "hola mundo";
    }
}
