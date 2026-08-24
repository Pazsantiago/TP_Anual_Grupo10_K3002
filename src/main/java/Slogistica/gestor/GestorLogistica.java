package Slogistica.gestor;

import Slogistica.dominio.camion.Camion;
import Slogistica.dominio.donacion.DonacionDTO;
import Slogistica.dominio.entrega.Entrega;
import Slogistica.dominio.entrega.EstadoEntrega;
import Slogistica.dominio.ruta.PlanDeRuta;
import Slogistica.planificador.IProveedorPlanificacionRutas;
import Slogistica.planificador.dto.RutaPlanificadaDTO;
import Slogistica.repositorios.RepoCamiones;
import Slogistica.repositorios.RepoDonacionesAsignadas;
import Slogistica.repositorios.RepoEntregas;
import Slogistica.repositorios.RepoPlanesDeRuta;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class GestorLogistica {

    private static final int TAMANIO_LOTE = 100;

    private final RepoDonacionesAsignadas repoDonacionesAsignadas;
    private final RepoCamiones repoCamiones;
    private final RepoPlanesDeRuta repoPlanesDeRuta;
    private final RepoEntregas repoEntregas;
    private final IProveedorPlanificacionRutas proveedorPlanificacionRutas;

    public GestorLogistica(RepoDonacionesAsignadas repoDonacionesAsignadas,
                           RepoCamiones repoCamiones,
                           RepoPlanesDeRuta repoPlanesDeRuta,
                           RepoEntregas repoEntregas,
                           IProveedorPlanificacionRutas proveedorPlanificacionRutas) {
        this.repoDonacionesAsignadas = repoDonacionesAsignadas;
        this.repoCamiones = repoCamiones;
        this.repoPlanesDeRuta = repoPlanesDeRuta;
        this.repoEntregas = repoEntregas;
        this.proveedorPlanificacionRutas = proveedorPlanificacionRutas;
    }

    /**
     * Punto de entrada: recibe las donaciones en estado "Asignación
     * Realizada" (enviadas por el Servicio de Donaciones) y solicita su
     * planificación al proveedor externo, en lotes de máximo 100
     * donaciones por restricción del proveedor.
     */
    public void solicitarPlanificacionDeRutas(List<DonacionDTO> donacionesAsignadas, String callbackUrl) {
        repoDonacionesAsignadas.recibirDonaciones(donacionesAsignadas);
        List<Camion> camionesDisponibles = repoCamiones.getCamionesDisponibles();

        for (int desde = 0; desde < donacionesAsignadas.size(); desde += TAMANIO_LOTE) {
            int hasta = Math.min(desde + TAMANIO_LOTE, donacionesAsignadas.size());
            List<DonacionDTO> lote = donacionesAsignadas.subList(desde, hasta);
            proveedorPlanificacionRutas.solicitarPlanificacion(lote, camionesDisponibles, callbackUrl);
        }
    }

    /**
     * Se ejecuta cuando el proveedor externo notifica, vía callback, el
     * resultado de la planificación: por cada camión, una lista ordenada
     * de destinos. Crea el PlanDeRuta y registra cada entrega.
     */
    public List<PlanDeRuta> registrarPlanificacionRecibida(List<RutaPlanificadaDTO> rutasPlanificadas) {
        List<PlanDeRuta> planesCreados = new ArrayList<>();

        for (RutaPlanificadaDTO rutaPlanificada : rutasPlanificadas) {
            Optional<Camion> camion = repoCamiones.buscarPorPatente(rutaPlanificada.getPatenteCamion());
            if (camion.isEmpty()) {
                continue;
            }

            PlanDeRuta plan = new PlanDeRuta(null, camion.get());
            plan.asignarRuta(rutaPlanificada.getDestinosOrdenados());
            repoPlanesDeRuta.guardar(plan);
            planesCreados.add(plan);

            for (DonacionDTO donacion : rutaPlanificada.getDestinosOrdenados()) {
                registrarEntregaDeDonacion(camion.get(), donacion);
            }

            repoDonacionesAsignadas.quitarDonaciones(rutaPlanificada.getDestinosOrdenados());
        }
        return planesCreados;
    }

    /**
     * Crea el registro de trazabilidad de una donación planificada en un camión.
     */
    public Entrega registrarEntregaDeDonacion(Camion camion, DonacionDTO donacion) {
        Entrega entrega = new Entrega(null, donacion, camion);
        return repoEntregas.guardar(entrega);
    }

    /**
     * El chofer marca el inicio de su ruta: sus entregas pasan a "En traslado".
     */
    public PlanDeRuta iniciarRuta(Integer idPlan) {
        PlanDeRuta plan = repoPlanesDeRuta.buscarPorId(idPlan)
                .orElseThrow(() -> new IllegalArgumentException("No existe el plan de ruta " + idPlan));
        plan.iniciarRuta();

        repoEntregas.buscarPorCamion(plan.getCamion().getPatente()).stream()
                .filter(e -> e.getEstado() == EstadoEntrega.LISTA_PARA_ENTREGAR)
                .forEach(Entrega::iniciarTraslado);

        return plan;
    }

    public PlanDeRuta finalizarRuta(Integer idPlan) {
        PlanDeRuta plan = repoPlanesDeRuta.buscarPorId(idPlan)
                .orElseThrow(() -> new IllegalArgumentException("No existe el plan de ruta " + idPlan));
        plan.finalizarRuta();
        return plan;
    }

    /**
     * La entidad beneficiaria confirma que recibió la donación (con fotos opcionales).
     */
    public Entrega entregarDonacion(Integer idEntrega, List<String> fotos) {
        Entrega entrega = repoEntregas.buscarPorId(idEntrega)
                .orElseThrow(() -> new IllegalArgumentException("No existe la entrega " + idEntrega));
        entrega.confirmarRecepcion(fotos);
        return entrega;
    }

    /**
     * La entidad beneficiaria (o la logística) informa que la entrega no se pudo concretar.
     */
    public Entrega informarNoEntregaDeDonacion(Integer idEntrega, String motivo) {
        Entrega entrega = repoEntregas.buscarPorId(idEntrega)
                .orElseThrow(() -> new IllegalArgumentException("No existe la entrega " + idEntrega));
        entrega.marcarNoRecibida(motivo);
        return entrega;
    }

    /**
     * Deja disponible el estado actual de todas las entregas para que otros
     * servicios (por ejemplo, Donaciones) lo consulten y actualicen su
     * propia trazabilidad. Logística nunca llama a otros servicios.
     */
    public List<Entrega> informarEstadoDeDonaciones() {
        return repoEntregas.getEntregas();
    }
}
