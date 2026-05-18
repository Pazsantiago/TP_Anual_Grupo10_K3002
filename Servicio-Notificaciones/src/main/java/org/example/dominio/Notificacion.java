package ar.edu.utn.donatrack.notificaciones.dominio;

import java.time.LocalDateTime;

public class Notificacion {

    public enum Tipo {
        BIENVENIDA,
        CREDENCIALES_ACCESO,
        DONACION_ASIGNADA,
        DONACION_ENTREGADA,
        MISION_CUMPLIDA,
        ASCENSO_CATEGORIA
    }

    private final String destinatario;   // correo o teléfono según canal
    private final String asunto;
    private final String cuerpo;
    private final Tipo tipo;
    private final LocalDateTime fechaEnvio;

    public Notificacion(String destinatario, String asunto, String cuerpo, Tipo tipo) {
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.cuerpo = cuerpo;
        this.tipo = tipo;
        this.fechaEnvio = LocalDateTime.now();
    }

    public String getDestinatario() { return destinatario; }
    public String getAsunto() { return asunto; }
    public String getCuerpo() { return cuerpo; }
    public Tipo getTipo() { return tipo; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }

    @Override
    public String toString() {
        return "[" + tipo + "] → " + destinatario + " | " + asunto;
    }
}
