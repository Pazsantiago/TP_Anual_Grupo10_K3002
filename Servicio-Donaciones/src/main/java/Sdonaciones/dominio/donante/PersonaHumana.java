package Sdonaciones.dominio.donador;

import org.example.excepciones.ContactoObligatorioException;

import java.util.List;
public class PersonaHumana extends Donador{
    String apellido;
    int edad;
    String genero;

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(List<Contacto> contactos) {
        this.contactos = contactos;
    }

    String direccion;
    List<Contacto> contactos;
    public PersonaHumana(String tipoD, String doc, String nom, String em, String tel) {
        super(tipoD, doc, nom, em, tel);
    }
}