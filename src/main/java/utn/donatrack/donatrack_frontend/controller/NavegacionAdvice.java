package utn.donatrack.donatrack_frontend.controller;

import java.util.List;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import utn.donatrack.donatrack_frontend.viewmodel.NavItem;

/**
 * Menu principal: se publica en el modelo de TODAS las paginas.
 * Para agregar una seccion: sumar una linea aca (aparece en la barra y en la pagina de inicio).
 */
@ControllerAdvice
public class NavegacionAdvice {

    private static final List<NavItem> ITEMS = List.of(
        new NavItem("donantes", "Donantes", "/donantes",
            "Personas y organizaciones que donan, con sus donaciones."),
        new NavItem("entidades", "Entidades", "/entidades",
            "Entidades beneficiarias y las necesidades que declararon."),
        new NavItem("asignaciones", "Asignaciones", "/asignaciones",
            "Ranking de entidades que proponen los algoritmos de asignación."),
        new NavItem("incentivos", "Incentivos", "/incentivos",
            "Misiones, insignias y ranking mensual de donantes."),
        new NavItem("logistica", "Logística", "/logistica",
            "Flota de camiones, bultos y entregas pendientes."),
        new NavItem("notificaciones", "Notificaciones", "/notificaciones",
            "Mensajes enviados por correo, SMS y WhatsApp."));

    @ModelAttribute("navegacion")
    public List<NavItem> navegacion() {
        return ITEMS;
    }
}
