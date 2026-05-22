package Sdonaciones.Importador;

/**
 * Primera iteración del Servicio de Notificaciones.
 * Opera con datos primitivos para evitar dependencias circulares entre módulos.
 * En entregas posteriores se integrará con canales reales (SMTP, WhatsApp, etc.).
 */
public interface ServicioNotificaciones {

    /** Envía correo de bienvenida al registrarse en la plataforma. */
    void notificarBienvenida(String correoDestinatario, String nombreDestinatario);

    /** Envía credenciales de acceso (importación CSV). */
    void notificarCredencialesAcceso(String correoDestinatario);

    /** Notifica al donador que su donación segmentada fue asignada a una entidad. */
    void notificarDonacionAsignada(String correoDestinatario, String subcategoriaNombre);

    /** Notifica al donador que su donación fue entregada a la entidad beneficiaria. */
    void notificarDonacionEntregada(String correoDestinatario, String subcategoriaNombre);
}
