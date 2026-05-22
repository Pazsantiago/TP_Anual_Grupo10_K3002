package Snotificaciones.dominio;

import java.time.LocalDateTime;

public class Notificacion {



    private final String destinatario;   // correo o teléfono según canal
    private final String asunto;
    private final String cuerpo;
    private final TipoNoti tipo;
    private final LocalDateTime fechaEnvio;

    public Notificacion(String destinatario, String asunto, String cuerpo, TipoNoti tipo) {
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.cuerpo = cuerpo;
        this.tipo = tipo;
        this.fechaEnvio = LocalDateTime.now();
    }

    public String getDestinatario() { return destinatario; }
    public String getAsunto() { return asunto; }
    public String getCuerpo() { return cuerpo; }
    public TipoNoti getTipo() { return tipo; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }

    @Override
    public String toString() {
        return "[" + tipo + "] → " + destinatario + " | " + asunto + "\n"+cuerpo;
    }
}
