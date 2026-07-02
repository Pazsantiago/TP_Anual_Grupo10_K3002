package Sdonaciones.dominio.entidad;

import Sdonaciones.dominio.necesidad.Necesidad;

import java.util.ArrayList;
import java.util.List;
public class EntidadBeneficiaria {

    private String razonSocial;
    private String telefono;
    private String correoRepresentante;
    private String direccion;

    public List<Necesidad> getNecesidadesHistoricas() {
        return necesidadesHistoricas;
    }

    private List<Necesidad> necesidadesHistoricas;

    public List<Necesidad> getNecesidades() {
        return necesidades;
    }

    public void setNecesidades(List<Necesidad> necesidades) {
        this.necesidades = necesidades;
    }

    public void setNecesidadesHistoricas(List<Necesidad> necesidadesHistoricas) {
        this.necesidadesHistoricas = necesidadesHistoricas;
    }

    private List<Necesidad> necesidades;

    public EntidadBeneficiaria(
            String razonSocial,
            String telefono,
            String correoRepresentante,
            String direccion
    ) {
        this.razonSocial = razonSocial;
        this.telefono = telefono;
        this.correoRepresentante = correoRepresentante;
        this.direccion = direccion;

        this.necesidadesHistoricas = new ArrayList<>();
    }


    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreoRepresentante() { return correoRepresentante; }
    public void setCorreoRepresentante(String correoRepresentante) { this.correoRepresentante = correoRepresentante; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

}
