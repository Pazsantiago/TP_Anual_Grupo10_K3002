package Sdonaciones.dominio.donante;

import java.util.List;

public abstract class Donante{
    private String tipoDoc;
    private String documento;
    private String nombre;
    private String correoElectronico;
    private String telefono;
    private List<MedioContacto> contactos;



    public Donante(String tipoDoc, String documento, String nombre, String correoElectronico, String telefono) {
        this.tipoDoc = tipoDoc;
        this.documento = documento;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<MedioContacto> getContactos() {
        return contactos;
    }

    public void setContactos(List<MedioContacto> contactos) {
        this.contactos = contactos;
    }
}