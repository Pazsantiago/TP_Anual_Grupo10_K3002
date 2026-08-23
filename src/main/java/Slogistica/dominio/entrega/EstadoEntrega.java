package Slogistica.dominio.entrega;

/**
 * Estados propios de la entrega, dentro del Servicio de Logística.
 * No confundir con TipoEstadoDonacion del Servicio de Donaciones: son
 * dos máquinas de estado independientes que pertenecen a dominios
 * distintos. El Servicio de Donaciones actualiza el suyo consultando
 * (polling) el estado que Logística deja disponible.
 */
public enum EstadoEntrega {
    LISTA_PARA_ENTREGAR,
    EN_TRASLADO,
    ENTREGADA,
    NO_RECIBIDA
}
