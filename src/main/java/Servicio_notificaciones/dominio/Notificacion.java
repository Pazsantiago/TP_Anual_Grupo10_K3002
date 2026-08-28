package Servicio_notificaciones.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notificacion {

    private UUID id;
    private Destinatario destinatario;
    private MedioNotificacion medioNotificacion;
    private String asunto;
    private String cuerpo;
    private TipoNotificacion tipoNotificacion;
    private LocalDateTime fechaEnvio;
    private EstadoNotificacion estadoNotificacion;
    private LocalDateTime fechaCreacion;
    //private String eventoOrigen;
    // private String referenciaId;
    private String error;


    public Notificacion(Destinatario destinatario, MedioNotificacion medioNotificacion, String asunto,
                        String cuerpo) {
        this.id = UUID.randomUUID();
        this.destinatario = destinatario;
        this.medioNotificacion = medioNotificacion;
        this.asunto = asunto;
        this.cuerpo = cuerpo;
        this.fechaCreacion = LocalDateTime.now();
        this.estadoNotificacion = EstadoNotificacion.PENDIENTE;

    }

    public Destinatario getDestinatario() { return destinatario; }
    public String getAsunto() { return asunto; }
    public String getCuerpo() { return cuerpo; }
    public TipoNotificacion getTipo() { return tipoNotificacion; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public String getError() { return error; }

    public MedioNotificacion getMedioNotificacion() {
        return medioNotificacion;
    }

    public EstadoNotificacion getEstadoNotificacion() {
        return estadoNotificacion;
    }

    public TipoNotificacion getTipoNotificacion() {
        return tipoNotificacion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public UUID getId() {
        return id;
    }

    public void marcarCompletada(){
        this.estadoNotificacion = EstadoNotificacion.ENVIADA;
        this.fechaEnvio = LocalDateTime.now();
    }

    public void marcarFallida(String error){
        this.estadoNotificacion = EstadoNotificacion.FALLIDA;
        this.error = error;
    }

    //getters y setters

    @Override
    public String toString() {
        return "[" + tipoNotificacion + "] → " + destinatario.getNombre() + " | " + asunto + "\n"+cuerpo;
    }

}
