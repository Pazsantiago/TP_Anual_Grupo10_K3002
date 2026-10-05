package Servicio_notificaciones.dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "destinatario")
public class Destinatario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nombre", length = 150)
  private String nombre;

  @Column(name = "email", length = 150)
  private String email;

  @Column(name = "telefono", length = 30)
  private String telefono;

  @Column(name = "whatsapp", length = 30)
  private String whatsapp;

  /** Requerido por JPA/Hibernate. */
  protected Destinatario() {
  }

  public Destinatario(String nombre, String email, String telefono, String whatsapp) {
    this.nombre = nombre;
    this.email = email;
    this.telefono = telefono;
    this.whatsapp = whatsapp;
  }

  public Long getId() {
    return id;
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
