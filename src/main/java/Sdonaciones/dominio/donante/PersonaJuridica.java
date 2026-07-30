package Sdonaciones.dominio.donante;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class PersonaJuridica extends Persona {
    private String razonSocial;
    private TipoPersonaJuridica tipoJuridica;
    private String rubro;
    private List<Representante> representantes;

//    public TipoPersonaJuridica getTipoEmpresa() {
//        return tipo;
//    }
//
//    public void setTipoEmpresa(TipoPersonaJuridica tipoPersonaJuridica) {
//        this.tipo = tipoPersonaJuridica;
//    }
//
//    public String getRubro() {
//        return rubro;
//    }
//
//    public void setRubro(String rubro) {
//        this.rubro = rubro;
//    }
//
//    public List<Representante> getRepresentantes() {
//        return representantes;
//    }
//
//    public void agregarRepresentantes(Representante representante) {
//        this.representantes.add(representante);
//    }
//
//    public PersonaJuridica(String tipoD, String doc, String nom) {
//        super(tipoD, doc);
//        this.razonSocial = nom;
//    }

}