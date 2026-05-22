package Sdonaciones.dominio.donante;

import java.util.List;
public class PersonaHumana extends Donante{
    private String apellido;
    private int edad;
    private String genero;
    private String direccion;


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


    public PersonaHumana(String tipoD, String doc, String nom, String em, String tel) {
        super(tipoD, doc, nom, em, tel);
    }
}