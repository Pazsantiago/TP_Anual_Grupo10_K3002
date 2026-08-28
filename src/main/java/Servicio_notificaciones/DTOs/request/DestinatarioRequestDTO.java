package Servicio_notificaciones.DTOs.request;

public class DestinatarioRequestDTO {

  String nombre;
  String telefono;
  String email;
  String whatsapp;


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

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public void setWhatsapp(String whatsapp) {
    this.whatsapp = whatsapp;
  }
}
