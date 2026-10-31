package utn.donatrack.donatrack_frontend.viewmodel;

/**
 * Opciones del select. name() es lo que se envia al servicio:
 * tiene que coincidir con su enum MedioNotificacion.
 */
public enum MedioVista {
    EMAIL("Correo electrónico"),
    SMS("SMS"),
    WHATSAPP("WhatsApp");

    private final String etiqueta;

    MedioVista(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
