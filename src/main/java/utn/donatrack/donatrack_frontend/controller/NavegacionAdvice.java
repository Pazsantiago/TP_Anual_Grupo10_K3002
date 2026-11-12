package utn.donatrack.donatrack_frontend.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import utn.donatrack.donatrack_frontend.viewmodel.Miga;
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
        new NavItem("donaciones", "Donaciones", "/donaciones",
            "Cargar donaciones en el depósito y seguir su trazabilidad."),
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

    /** Estados de una donacion (Figura 2 del enunciado), en orden de ciclo de vida, para los filtros. */
    @ModelAttribute("estadosDonacion")
    public Map<String, String> estadosDonacion() {
        Map<String, String> estados = new LinkedHashMap<>();
        estados.put("EN_DEPOSITO", "En depósito");
        estados.put("ASIGNACION_REALIZADA", "Asignación realizada");
        estados.put("LISTA_PARA_ENTREGAR", "Lista para entregar");
        estados.put("EN_TRASLADO", "En traslado");
        estados.put("ENTREGADA", "Entregada");
        estados.put("ENTREGA_FALLIDA", "Entrega fallida");
        estados.put("VENCIDA", "Vencida");
        return estados;
    }

    // ---- Migas de pan: se arman solas desde la URL, asi cada pagina nueva las tiene sin tocar nada ----

    private static final Map<String, String> ETIQUETAS = Map.ofEntries(
        Map.entry("panel", "Panel"), Map.entry("donantes", "Donantes"), Map.entry("donaciones", "Donaciones"),
        Map.entry("entidades", "Entidades"), Map.entry("necesidades", "Necesidades"),
        Map.entry("asignaciones", "Asignaciones"), Map.entry("incentivos", "Incentivos"),
        Map.entry("misiones", "Categorías y misiones"), Map.entry("gestion", "Gestión de perfiles"), Map.entry("logistica", "Logística"),
        Map.entry("camiones", "Camiones"), Map.entry("seguimiento", "Seguimiento"), Map.entry("ruta", "Mi ruta"),
        Map.entry("entregas-fallidas", "Entregas no recibidas"), Map.entry("recepcion", "Confirmar recepción"),
        Map.entry("notificaciones", "Notificaciones"), Map.entry("galeria", "Galería"), Map.entry("mapa", "Mapa"),
        Map.entry("legal", "Información legal y de privacidad"), Map.entry("login", "Iniciar sesión"),
        Map.entry("registro", "Registro"), Map.entry("importar", "Importar CSV"), Map.entry("editar", "Editar"),
        Map.entry("nuevo", "Nuevo"), Map.entry("nueva", "Nueva"));

    /** Etiquetas que dependen del tramo anterior ("nuevo" no significa lo mismo en donantes que en camiones). */
    private static final Map<String, String> ETIQUETAS_EN_CONTEXTO = Map.of(
        "donantes/nuevo", "Registrar donante", "camiones/nuevo", "Nuevo camión", "donaciones/nueva", "Cargar donación",
        "entidades/nueva", "Registrar entidad", "necesidades/nueva", "Registrar necesidad",
        "registro/donante", "Como donante", "registro/entidad", "Como entidad beneficiaria");

    /** Rutas que existen como pagina (GET). Un tramo intermedio que no coincide se muestra sin enlace, para no llevar a un 404. */
    private static final Pattern RUTA_NAVEGABLE = Pattern.compile(
        "^/(panel|donantes|donaciones|entidades|asignaciones|incentivos|logistica|notificaciones|galeria|mapa|legal|login)$"
            + "|^/logistica/(camiones|seguimiento|ruta|entregas-fallidas)$|^/incentivos/(misiones|gestion)$"
            + "|^/donantes/\\d+/donaciones$|^/entidades/\\d+/(necesidades|donaciones)$"
            + "|^/asignaciones/\\d+$|^/incentivos/donantes/\\d+$");

    @ModelAttribute("migas")
    public List<Miga> migas(HttpServletRequest request) {
        List<Miga> migas = new ArrayList<>();
        migas.add(new Miga("Inicio", "/"));
        String uri = request.getRequestURI().substring(request.getContextPath().length());
        if (uri.length() <= 1) {
            return migas;
        }
        String[] partes = uri.substring(1).split("/");
        StringBuilder ruta = new StringBuilder();
        for (int i = 0; i < partes.length; i++) {
            ruta.append('/').append(partes[i]);
            boolean ultima = i == partes.length - 1;
            String actual = ruta.toString();
            migas.add(new Miga(etiqueta(partes[i], i > 0 ? partes[i - 1] : ""),
                ultima || !RUTA_NAVEGABLE.matcher(actual).matches() ? null : actual));
        }
        return migas;
    }

    private static String etiqueta(String tramo, String anterior) {
        String enContexto = ETIQUETAS_EN_CONTEXTO.get(anterior + "/" + tramo);
        if (enContexto != null) {
            return enContexto;
        }
        String conocida = ETIQUETAS.get(tramo);
        if (conocida != null) {
            return conocida;
        }
        return "N.º " + (tramo.length() > 12 ? tramo.substring(0, 8) + "…" : tramo);   // identificador
    }
}
