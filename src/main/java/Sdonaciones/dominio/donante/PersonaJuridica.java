package Sdonaciones.dominio.donante;

import java.util.List;

public class PersonaJuridica extends TipoPersona {
    private String razonSocial;
    private TipoPersonaJuridica tipo;
    String rubro;
    List<Representante> representantes;

    public TipoPersonaJuridica getTipoEmpresa() {
        return tipo;
    }

    public void setTipoEmpresa(TipoPersonaJuridica tipoPersonaJuridica) {
        this.tipo = tipoPersonaJuridica;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public List<Representante> getRepresentantes() {
        return representantes;
    }

    public void agregarRepresentantes(Representante representante) {
        this.representantes.add(representante);
    }
    public PersonaJuridica(String tipoD, String doc, String nom) {
        super(tipoD, doc);
        this.razonSocial = nom;
    }

}