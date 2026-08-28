package Servicio_notificaciones.dominio;

public class Destinatario {

  private String nombre;
  private String email;
  private String telefono;
  private String whatsapp;

  public Destinatario(String nombre, String email, String telefono, String whatsapp) {
    this.nombre = nombre;
    this.email = email;
    this.telefono = telefono;
    this.whatsapp = whatsapp;
  }

  public String getNombre() {
    return nombre;
  }

  public String getEmail() {
    return email;
  }

  public String getTelefono() {
    return telefono;
  }

  public String getWhatsapp() {
    return whatsapp;
  }

}
