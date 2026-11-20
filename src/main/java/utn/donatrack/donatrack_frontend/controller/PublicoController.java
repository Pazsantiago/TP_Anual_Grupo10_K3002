package utn.donatrack.donatrack_frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Paginas de acceso publico (sin login): galeria, mapa, informacion legal, inicio de sesion y registro.
 * Pendiente de los servicios: fotos y entregas para galeria/mapa (atributos "fotos" y "entregas") y el
 * POST /login (Entrega 6: servicio de autenticacion / SSO).
 */
@Controller
public class PublicoController {

    public PublicoController() {
        
    }

    @GetMapping("/galeria")
    public String galeria() {
        return "publico/galeria";
    }

    @GetMapping("/mapa")
    public String mapa() {
        return "publico/mapa";
    }

    @GetMapping("/legal")
    public String legal() {
        return "publico/legal";
    }
}

//
//    @GetMapping("/login")
//    public String login() {
//        return "auth/login";
//    }
//
//    @GetMapping("/registro/donante")
//    public String registroDonante() {
//        return "auth/registro-donante";
//    }
//
//    @GetMapping("/registro/entidad")
//    public String registroEntidad() {
//        return "auth/registro-entidad";
//    }
//
//    /** Auto-registro de donante: la persona define su propia contrasena. Post/Redirect/Get. */
//    @PostMapping("/registro/donante")
//    public String registrarDonante(@ModelAttribute DonanteForm form, RedirectAttributes flash) {
//        String error = form.validar(true);
//        if (error != null) {
//            flash.addFlashAttribute("errorServicio", error);
//            return "redirect:/registro/donante";
//        }
//        try {
//            donaciones.crearDonante(form.toRequest(true));
//        } catch (ServicioExternoException e) {
//            flash.addFlashAttribute("errorServicio", e.getMessage());
//            return "redirect:/registro/donante";
//        }
//        flash.addFlashAttribute("exito", "Te registraste correctamente. Ya podés iniciar sesión.");
//        return "redirect:/login";
//    }
//
//    @PostMapping("/registro/entidad")
//    public String registrarEntidad(@ModelAttribute EntidadForm form, RedirectAttributes flash) {
//        String error = form.validar(true);
//        if (error != null) {
//            flash.addFlashAttribute("errorServicio", error);
//            return "redirect:/registro/entidad";
//        }
//        try {
//            donaciones.crearEntidad(form.toRequest(true));
//        } catch (ServicioExternoException e) {
//            flash.addFlashAttribute("errorServicio", e.getMessage());
//            return "redirect:/registro/entidad";
//        }
//        flash.addFlashAttribute("exito", "Registramos tu entidad. Ya podés iniciar sesión.");
//        return "redirect:/login";
//    }