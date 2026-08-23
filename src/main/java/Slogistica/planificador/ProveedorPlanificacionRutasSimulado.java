package Slogistica.planificador;

import Slogistica.dominio.camion.Camion;
import Slogistica.dominio.donacion.DonacionDTO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementación simulada del proveedor externo de planificación de rutas.
 * Por ahora solo registra la solicitud; en una entrega posterior se
 * reemplaza por un cliente HTTP real que invoque al proveedor externo,
 * el cual llamará por su cuenta a /api/logistica/planificacion/callback
 * con el resultado.
 */
@Component
public class ProveedorPlanificacionRutasSimulado implements IProveedorPlanificacionRutas {

    @Override
    public void solicitarPlanificacion(List<DonacionDTO> loteDonaciones,
                                        List<Camion> camionesDisponibles,
                                        String callbackUrl) {
        System.out.printf(
                "[Proveedor de Rutas - SIMULADO] Lote de %d donaciones a planificar entre %d camiones disponibles. Resultado esperado en %s%n",
                loteDonaciones.size(), camionesDisponibles.size(), callbackUrl
        );
    }
}
