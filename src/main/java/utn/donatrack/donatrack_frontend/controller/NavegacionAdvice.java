package utn.donatrack.donatrack_frontend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import utn.donatrack.donatrack_frontend.viewmodel.Miga;
import utn.donatrack.donatrack_frontend.viewmodel.NavItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@ControllerAdvice
public class NavegacionAdvice {

    @ModelAttribute("navegacion")
    public List<NavItem> navegacion(HttpSession session) {
        String rol = (session != null) ? (String) session.getAttribute("userRol") : null;
        if (rol == null) {
            return List.of();
        }

        String rolLimpio = rol.replace("ROLE_", "").toUpperCase();

        switch (rolLimpio) {
            case "ADMIN":
                return List.of(
                        new NavItem("donantes", "Donantes", "/donantes", "Gestión de donantes e importación CSV."),
                        new NavItem("donaciones", "Cargar Donación", "/donaciones/nueva", "Registrar donaciones de bienes en depósito."),
                        new NavItem("segmentadas", "Donaciones Segmentadas", "/donaciones/segmentadas", "Filtrar y controlar lotes segmentados."),
                        new NavItem("entidades", "Entidades Beneficiarias", "/entidadesBeneficiarias", "Gestión de entidades y necesidades."),
                        new NavItem("asignaciones", "Asignar Donaciones", "/asignaciones/nueva", "Asignar lotes a necesidades de entidades."),
                        new NavItem("rankings", "Rankings de Asignación", "/rankings", "Algoritmos de compatibilidad y prioridad."),
                        new NavItem("logistica", "Logística", "/logistica", "Control de flota, camiones y hojas de ruta."),
                        new NavItem("notificaciones", "Notificaciones", "/notificaciones", "Historial de mensajes e integraciones.")
                );

            case "DONANTE":
                return List.of(
                        new NavItem("segmentadas", "Mis Donaciones Segmentadas", "/donaciones/segmentadas", "Filtrar y seguir el estado de tus lotes donados."),
                        new NavItem("donaciones", "Mis Donaciones Registradas", "/donaciones", "Ver el historial completo de donaciones."),
                        new NavItem("incentivos", "Incentivos y Misiones", "/incentivos", "Tus insignias, puntos y el ranking mensual."),
                        new NavItem("seguimiento", "Seguimiento de Envíos", "/logistica/seguimiento", "Seguir en tiempo real el traslado de donaciones.")
                );

            case "ENTIDAD":
                return List.of(
                        new NavItem("necesidades", "Mis Necesidades", "/necesidades", "Registrar y gestionar necesidades de tu organización."),
                        new NavItem("asignadas", "Donaciones Asignadas", "/asignaciones", "Consultar donaciones asignadas a tu entidad."),
                        new NavItem("rankings", "Rankings de Prioridad", "/rankings", "Ver la posición de tu entidad en los algoritmos."),
                        new NavItem("seguimiento", "Seguimiento de Envíos", "/logistica/seguimiento", "Ver el recorrido de los camiones a tu sede.")
                );

            case "CHOFER":
                return List.of(
                        new NavItem("ruta", "Mi Ruta de Entrega", "/ruta", "Consultar hoja de ruta e iniciar recorrido."),
                        new NavItem("logistica", "Estado de Envíos", "/logistica", "Confirmar entregas o reportar incidencias.")
                );

            default:
                return List.of();
        }
    }

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

    private static final Map<String, String> ETIQUETAS = Map.ofEntries(
            Map.entry("panel", "Panel"),
            Map.entry("home", "Inicio"),
            Map.entry("donantes", "Donantes"),
            Map.entry("admin", "Administrador"),
            Map.entry("donaciones", "Donaciones"),
            Map.entry("entidades", "Entidades"),
            Map.entry("entidadesBeneficiarias", "Entidades beneficiarias"),
            Map.entry("necesidades", "Necesidades"),
            Map.entry("asignaciones", "Asignaciones"),
            Map.entry("asignadas", "Donaciones asignadas"),
            Map.entry("rankings", "Rankings"),
            Map.entry("segmentadas", "Donaciones segmentadas"),
            Map.entry("vencida", "Marcar vencida"),
            Map.entry("repetidas", "Entidades repetidas"),
            Map.entry("incentivos", "Incentivos"),
            Map.entry("ranking-mensual", "Ranking mensual"),
            Map.entry("misiones", "Categorías y misiones"),
            Map.entry("gestion", "Gestión de perfiles"),
            Map.entry("logistica", "Logística"),
            Map.entry("camiones", "Camiones"),
            Map.entry("seguimiento", "Seguimiento"),
            Map.entry("ubicaciones", "Ubicaciones"),
            Map.entry("ruta", "Mi ruta"),
            Map.entry("entregas-fallidas", "Entregas no recibidas"),
            Map.entry("recepcion", "Confirmar recepción"),
            Map.entry("notificaciones", "Notificaciones"),
            Map.entry("galeria", "Galería"),
            Map.entry("mapa", "Mapa"),
            Map.entry("legal", "Información legal y de privacidad"),
            Map.entry("login", "Iniciar sesión"),
            Map.entry("registro", "Registro"),
            Map.entry("importar", "Importar CSV"),
            Map.entry("importador", "Importar CSV"),
            Map.entry("editar", "Editar"),
            Map.entry("nuevo", "Nuevo"),
            Map.entry("nueva", "Nueva"),
            Map.entry("agregar-segmentada", "Agregar segmentada")
    );

    private static final Map<String, String> ETIQUETAS_EN_CONTEXTO = Map.ofEntries(
            Map.entry("donantes/nuevo", "Registrar donante"),
            Map.entry("nuevo/admin", "Registrar donante por Admin"),
            Map.entry("donantes/importador", "Importar donantes CSV"),
            Map.entry("donaciones/nueva", "Cargar donación"),
            Map.entry("donaciones/segmentadas", "Donaciones segmentadas"),
            Map.entry("donaciones/vencida", "Marcar vencida"),
            Map.entry("entidadesBeneficiarias/nueva", "Registrar entidad"),
            Map.entry("entidades/nueva", "Registrar entidad"),
            Map.entry("necesidades/nueva", "Registrar necesidad"),
            Map.entry("asignaciones/nueva", "Nueva asignación"),
            Map.entry("asignaciones/agregar-segmentada", "Agregar segmentada"),
            Map.entry("rankings/repetidas", "Entidades repetidas"),
            Map.entry("camiones/nuevo", "Nuevo camión"),
            Map.entry("ruta/iniciar", "Iniciar ruta"),
            Map.entry("registro/donante", "Como donante"),
            Map.entry("registro/entidad", "Como entidad beneficiaria")
    );

    private static final Pattern RUTA_NAVEGABLE = Pattern.compile(
            "^/(panel|donantes|donaciones|entidadesBeneficiarias|entidades|necesidades|asignaciones|asignadas|rankings|incentivos|logistica|notificaciones|galeria|mapa|legal|login)$"
                    + "|^/donantes/(nuevo|nuevo/admin|importador)$"
                    + "|^/donaciones/(nueva|segmentadas|vencida|home)$"
                    + "|^/(entidadesBeneficiarias|entidades)/nueva$"
                    + "|^/necesidades/nueva$"
                    + "|^/asignaciones/(nueva|agregar-segmentada)$"
                    + "|^/rankings/repetidas$"
                    + "|^/logistica/(panel|camiones|seguimiento|seguimiento/ubicaciones|ruta|ruta/iniciar|entregas-fallidas)$"
                    + "|^/incentivos/(misiones|gestion|ranking-mensual)$"
                    + "|^/donantes/\\d+/donaciones$"
                    + "|^/(entidadesBeneficiarias|entidades)/\\d+/(necesidades|donaciones)$"
                    + "|^/asignaciones/\\d+$|^/incentivos/donantes/\\d+$"
    );

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
        return "N.º " + (tramo.length() > 12 ? tramo.substring(0, 8) + "…" : tramo);
    }
}