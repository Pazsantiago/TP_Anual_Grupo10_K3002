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
}
