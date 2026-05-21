/*
package Sdonaciones.servicios;

import ar.edu.utn.donatrack.dominio.bien.Bien;
import ar.edu.utn.donatrack.dominio.bien.BienPerecedero;
import ar.edu.utn.donatrack.dominio.donacion.Donacion;
import ar.edu.utn.donatrack.dominio.donacion.EstadoDonacion;
import ar.edu.utn.donatrack.dominio.donador.Donador;
import ar.edu.utn.donatrack.dominio.entidad.EntidadBeneficiaria;
import ar.edu.utn.donatrack.dominio.necesidad.Necesidad;
import ar.edu.utn.donatrack.excepciones.DonadorNoEncontradoException;
import ar.edu.utn.donatrack.notificaciones.servicios.ServicioNotificaciones;
import ar.edu.utn.donatrack.repositorios.RepositorioDonaciones;
import ar.edu.utn.donatrack.repositorios.RepositorioDonadores;
import ar.edu.utn.donatrack.repositorios.RepositorioEntidades;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * Servicio principal de donaciones.
 * Responsabilidades:
 *  - Registrar donadores (personas humanas y jurídicas)
 *  - Registrar donaciones y segmentarlas automáticamente por subcategoría
 *  - Gestionar entidades beneficiarias y sus necesidades
 *  - Actualizar estados de donaciones (vencimiento, etc.)
 *//*

public class ServicioDonaciones {

    private final RepositorioDonadores repositorioDonadores;
    private final RepositorioDonaciones repositorioDonaciones;
    private final RepositorioEntidades repositorioEntidades;
    private final ServicioNotificaciones servicioNotificaciones;

    public ServicioDonaciones(RepositorioDonadores repositorioDonadores,
                              RepositorioDonaciones repositorioDonaciones,
                              RepositorioEntidades repositorioEntidades,
                              ServicioNotificaciones servicioNotificaciones) {
        this.repositorioDonadores = repositorioDonadores;
        this.repositorioDonaciones = repositorioDonaciones;
        this.repositorioEntidades = repositorioEntidades;
        this.servicioNotificaciones = servicioNotificaciones;
    }

    // ─── Gestión de Donadores ────────────────────────────────────────────────

    public void registrarDonador(Donador donador) {
        if (repositorioDonadores.existePorCorreo(donador.getCorreoElectronico())) {
            throw new IllegalStateException(
                "Ya existe un donador con el correo: " + donador.getCorreoElectronico());
        }
        repositorioDonadores.guardar(donador);
        servicioNotificaciones.notificarBienvenida(
            donador.getCorreoElectronico(), donador.getCorreoElectronico());
    }

    public Donador buscarDonadorPorCorreo(String correo) {
        return repositorioDonadores.buscarPorCorreo(correo)
                .orElseThrow(() -> new DonadorNoEncontradoException(correo));
    }

    // ─── Registro de Donaciones ──────────────────────────────────────────────

    */
/**
     * Registra una donación completa y la segmenta automáticamente por subcategoría.
     *
     * La segmentación agrupa los bienes por subcategoría, generando una
     * DonacionSegmentada por cada subcategoría presente en la carga.
     * Para bienes perecederos con diferente fecha de vencimiento, se generan
     * donaciones segmentadas separadas.
     *
     * @param donador        donador que realiza la entrega
     * @param idAdministrador administrador que registra la donación en el depósito
     * @param descripcion    descripción general de la donación
     * @param bienes         lista de bienes entregados
     * @return la Donacion registrada con sus segmentos
     *//*

    public Donacion registrarDonacion(Donador donador, String idAdministrador,
                                      String descripcion, List<Bien> bienes) {
        if (bienes == null || bienes.isEmpty())
            throw new IllegalArgumentException("La donación debe contener al menos un bien.");

        Donacion donacion = new Donacion(donador, idAdministrador, descripcion);

        List<DonacionSegmentada> segmentos = segmentar(bienes);
        segmentos.forEach(donacion::agregarDonacionSegmentada);

        repositorioDonaciones.guardar(donacion);
        donador.agregarDonacion(donacion);

        return donacion;
    }

    */
/**
     * Segmenta la lista de bienes en DonacionesSegmentadas agrupadas por subcategoría.
     * Para perecederos con distinta fecha de vencimiento se generan entradas separadas.
     *//*

    private List<DonacionSegmentada> segmentar(List<Bien> bienes) {
        // Clave: nombre de subcategoría + fecha de vencimiento (si aplica)
        Map<String, DonacionSegmentada> segmentos = new LinkedHashMap<>();

        for (Bien bien : bienes) {
            String clave = generarClave(bien);
            // Por ahora cada bien genera su propia DonacionSegmentada.
            // En una siguiente iteración se puede acumular cantidades del mismo segmento.
            segmentos.put(clave + "_" + System.nanoTime(), new DonacionSegmentada(bien));
        }

        return List.copyOf(segmentos.values());
    }

    private String generarClave(Bien bien) {
        String base = bien.getSubcategoria().getNombre();
        if (bien instanceof BienPerecedero bp) {
            return base + "_vence_" + bp.getFechaVencimiento();
        }
        if (bien.getEstado() != null) {
            return base + "_" + bien.getEstado().name();
        }
        return base;
    }

    // ─── Gestión de estados ──────────────────────────────────────────────────

    */
/**
     * Marca como vencidas todas las donaciones segmentadas con bienes perecederos vencidos.
     * En producción esto se invocaría desde un scheduler periódico.
     *//*

    public void actualizarVencimientos() {
        repositorioDonaciones.listarSegmentadasPorEstado(EstadoDonacion.PENDIENTE_ASIGNACION)
                .forEach(ds -> {
                    if (ds.getBien() instanceof BienPerecedero bp && bp.estaVencido()) {
                        ds.marcarVencida();
                    }
                });
    }

    // ─── Gestión de Entidades Beneficiarias ──────────────────────────────────

    public void registrarEntidadBeneficiaria(EntidadBeneficiaria entidad) {
        repositorioEntidades.guardar(entidad);
    }

    public List<EntidadBeneficiaria> listarEntidades() {
        return repositorioEntidades.listarTodas();
    }

    public void registrarNecesidad(String razonSocialEntidad, Necesidad necesidad) {
        EntidadBeneficiaria entidad = repositorioEntidades
                .buscarPorRazonSocial(razonSocialEntidad)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Entidad no encontrada: " + razonSocialEntidad));
        entidad.registrarNecesidad(necesidad);
    }
}
*/
