package Sdonaciones.dominio.donante;

import java.util.List;
public class PersonaHumana extends TipoPersona{
    private String nombre;
    private Integer edad;
    private Genero genero;


    public PersonaHumana(String tipoD, String doc, String nom) {
        super(tipoD, doc);
        this.nombre = nom;
    }
}