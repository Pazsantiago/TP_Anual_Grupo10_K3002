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
                        new NavItem("asignaciones", "Asignar Donaciones", "/asignaciones/nueva", "Asignar lotes a necesidades de entidades."),
                        new NavItem("asignacionesNuevo", "Agregar Donaciones a asignada", "/asignaciones/agregar-segmentada", "Asignar lotes a necesidades de entidades."),
                        new NavItem("donacionesVencidas", "Indicar vencimiento de donacion", "/donaciones/vencidas", "Indicar el vencimiento de una donacion segmentada."),
                        new NavItem("rankings", "Rankings de Asignación", "/rankings", "Algoritmos de compatibilidad y prioridad."),
                        new NavItem("generar-ranking", "Generar rankings de asignación", "/rankings/generar", "Generar los rankings de entidades y donaciones"),
                        new NavItem("visualizar-repetidos-ranking", "Visualizar entidades repetidas en ranking", "/rankings/repetidas", "Visualizar entidades repetidas en rankings"),
                        new NavItem("logistica", "Logística", "/logistica", "Control de flota, camiones y hojas de ruta."),
                        new NavItem("incentivos", "Visualiza ranking mensual de donantes", "/incentivos", "Se pueden ver el ranking de los donantes")
                );

            case "DONANTE":
                return List.of(
                        new NavItem("donaciones", "Mis Donaciones Registradas", "/donaciones", "Ver el historial completo de donaciones."),
                        new NavItem("segmentadas", "Mis Donaciones Segmentadas", "/donaciones/segmentadas", "Filtrar y seguir el estado de tus lotes donados."),
                        new NavItem("entidades", "Entidades beneficiarias registradas", "/entidadesBeneficiarias", "Podrás ver todas las entidades beneficiarias registradas."),
                        new NavItem("incentivos", "Incentivos y Misiones", "/incentivos/perfil-donante", "Tus insignias, puntos y el ranking mensual."),
                        new NavItem("seguimiento", "Seguimiento de Envíos", "/logistica/seguimiento", "Seguir en tiempo real el traslado de donaciones."),
                        new NavItem("notificaciones", "Notificaciones", "/notificaciones", "Historial de mensajes e integraciones.")
                );

            case "ENTIDAD":
                return List.of(
                        new NavItem("necesidades", "Mis Necesidades", "/necesidades", "Registrar y gestionar necesidades de tu organización."),
                        new NavItem("asignadas", "Donaciones Asignadas", "/asignaciones", "Consultar donaciones asignadas a tu entidad."),
                        new NavItem("seguimiento", "Seguimiento de Envíos", "/logistica/seguimiento", "Ver el recorrido de los camiones a tu sede."),
                        new NavItem("notificaciones", "Notificaciones", "/notificaciones", "Historial de mensajes e integraciones.")
                );

            case "CHOFER":
                return List.of(
                        new NavItem("ruta", "Mi Ruta de Entrega", "/ruta", "Consultar hoja de ruta e iniciar recorrido."),
                        new NavItem("inicio-ruta", "Inicio de ruta", "/rutas/iniciar", "Confirmar inicio de ruta.")
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
            Map.entry("vencidas", "Indicar vencimiento"),
            Map.entry("generar", "Generar ranking"),
            Map.entry("repetidas", "Entidades repetidas"),
            Map.entry("incentivos", "Incentivos"),
            Map.entry("perfil-donante", "Perfil del donante"),
            Map.entry("ranking-mensual", "Ranking mensual"),
            Map.entry("misiones", "Categorías y misiones"),
            Map.entry("gestion", "Gestión de perfiles"),
            Map.entry("logistica", "Logística"),
            Map.entry("camiones", "Camiones"),
            Map.entry("seguimiento", "Seguimiento"),
            Map.entry("ubicaciones", "Ubicaciones"),
            Map.entry("ruta", "Mi ruta"),
            Map.entry("rutas", "Rutas"),
            Map.entry("iniciar", "Iniciar ruta"),
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
            Map.entry("donaciones/vencidas", "Indicar vencimiento"),
            Map.entry("entidadesBeneficiarias/nueva", "Registrar entidad"),
            Map.entry("entidades/nueva", "Registrar entidad"),
            Map.entry("necesidades/nueva", "Registrar necesidad"),
            Map.entry("asignaciones/nueva", "Nueva asignación"),
            Map.entry("asignaciones/agregar-segmentada", "Agregar segmentada"),
            Map.entry("rankings/generar", "Generar rankings"),
            Map.entry("rankings/repetidas", "Entidades repetidas"),
            Map.entry("incentivos/perfil-donante", "Perfil del donante"),
            Map.entry("camiones/nuevo", "Nuevo camión"),
            Map.entry("ruta/iniciar", "Iniciar ruta"),
            Map.entry("rutas/iniciar", "Inicio de ruta"),
            Map.entry("registro/donante", "Como donante"),
            Map.entry("registro/entidad", "Como entidad beneficiaria")
    );

    private static final Pattern RUTA_NAVEGABLE = Pattern.compile(
            "^/(panel|donantes|donaciones|entidadesBeneficiarias|entidades|necesidades|asignaciones|asignadas|rankings|incentivos|logistica|notificaciones|galeria|mapa|legal|login)$"
                    + "|^/donantes/(nuevo|nuevo/admin|importador)$"
                    + "|^/donaciones/(nueva|segmentadas|vencidas|home)$"
                    + "|^/(entidadesBeneficiarias|entidades)/nueva$"
                    + "|^/necesidades/nueva$"
                    + "|^/asignaciones/(nueva|agregar-segmentada)$"
                    + "|^/rankings/(generar|repetidas)$"
                    + "|^/incentivos/perfil-donante$"
                    + "|^/logistica/(panel|camiones|seguimiento|seguimiento/ubicaciones|ruta|ruta/iniciar|entregas-fallidas)$"
                    + "|^/rutas/iniciar$"
                    + "|^/ruta/iniciar$"
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