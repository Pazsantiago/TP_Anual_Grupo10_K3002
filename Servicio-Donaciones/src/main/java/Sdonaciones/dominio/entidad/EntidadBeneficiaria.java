package Sdonaciones.dominio.entidad;

import Sdonaciones.dominio.necesidad.Necesidad;

import java.util.ArrayList;
import java.util.List;
public class EntidadBeneficiaria {

    private String razonSocial;
    private String telefono;
    private String correoRepresentante;
    private String direccion;
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

        this.necesidades = new ArrayList<>();
    }

    public void agregarNecesidad(Necesidad necesidad) { necesidades.add(necesidad); }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreoRepresentante() { return correoRepresentante; }
    public void setCorreoRepresentante(String correoRepresentante) { this.correoRepresentante = correoRepresentante; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public List<Necesidad> getNecesidades() { return necesidades; }
    public void setNecesidades(List<Necesidad> necesidades) { this.necesidades = necesidades; }
}
