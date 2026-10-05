package Servicio_notificaciones.dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "notificacion")
public class Notificacion {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "id", columnDefinition = "CHAR(36)", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Destinatario destinatario;

    @Enumerated(EnumType.STRING)
    @Column(name = "medio_notificacion", length = 20, nullable = false)
    private MedioNotificacion medioNotificacion;

    @Column(name = "asunto", length = 200)
    private String asunto;

    @Column(name = "cuerpo", columnDefinition = "TEXT")
    private String cuerpo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_notificacion", length = 30)
    private TipoNotificacion tipoNotificacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_notificacion", length = 20, nullable = false)
    private EstadoNotificacion estadoNotificacion;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Column(name = "error", length = 1000)
    private String error;

    protected Notificacion() {
    }

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
        this.error = error != null && error.length() > 1000 ? error.substring(0, 1000) : error;
    }

    @Override
    public String toString() {
        return "[" + tipoNotificacion + "] → " + destinatario.getNombre() + " | " + asunto + "\n"+cuerpo;
    }

}
