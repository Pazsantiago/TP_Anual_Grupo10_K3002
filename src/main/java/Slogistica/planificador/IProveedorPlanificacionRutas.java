package Slogistica.planificador;

import Slogistica.dominio.camion.Camion;
import Slogistica.dominio.donacion.DonacionDTO;

import java.util.List;

/**
 * Representa al componente externo que arma las rutas óptimas de entrega.
 * El sistema le envía, por lote (máximo 100 donaciones por restricción
 * del proveedor), las donaciones a entregar junto con los camiones
 * disponibles. El proveedor responde de forma asincrónica invocando la
 * URL de callback provista.
 */
public interface IProveedorPlanificacionRutas {

    void solicitarPlanificacion(List<DonacionDTO> loteDonaciones,
                                 List<Camion> camionesDisponibles,
                                 String callbackUrl);
}
