package Snotificaciones.Notificacion;

import Snotificaciones.dominio.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de notificaciones por consola (Entrega 1).
 * Almacena el historial para facilitar tests.
 */
public class ServicioNotificacionesConsola implements ServicioNotificaciones {

    private final List<Notificacion> historial = new ArrayList<>();

    @Override
    public void notificarBienvenida(String correo, String nombre) {
        enviar(new Notificacion(correo,
            "¡Bienvenido/a a DonaTrack!",
            "Hola " + nombre + ", tu cuenta fue creada exitosamente.",
            TipoNoti.BIENVENIDA));
    }

    @Override
    public void notificarCredencialesAcceso(String correo) {
        enviar(new Notificacion(correo,
            "Tus credenciales de acceso a DonaTrack",
            "Tu cuenta fue migrada. Accedé con tu correo: " + correo,
                TipoNoti.CREDENCIALES_ACCESO));
    }

    @Override
    public void notificarDonacionAsignada(String correo, String subcategoria) {
        enviar(new Notificacion(correo,
            "Tu donación fue asignada",
            "Tu donación de '" + subcategoria + "' fue asignada a una entidad beneficiaria.",
                TipoNoti.DONACION_ASIGNADA));
    }

    @Override
    public void notificarDonacionEntregada(String correo, String subcategoria) {
        enviar(new Notificacion(correo,
            "Tu donación fue entregada",
            "La donación de '" + subcategoria + "' llegó a destino. ¡Gracias por tu aporte!",
                TipoNoti.DONACION_ENTREGADA));
    }

    private void enviar(Notificacion n) {
        historial.add(n);
        System.out.println("[NOTIFICACIÓN] " + n);
    }

    public List<Notificacion> getHistorial() {
        return List.copyOf(historial);
    }

}
