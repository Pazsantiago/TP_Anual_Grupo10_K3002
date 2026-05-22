package Sdonaciones.dominio.donante;

import java.util.ArrayList;
import java.util.List;

public class PersonaJuridica extends Donante {
    TipoEmpresa tipoEmpresa;
    String rubro;
    List<PersonaHumana> representantes;

    public TipoEmpresa getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public List<PersonaHumana> getRepresentantes() {
        return representantes;
    }

    public void setRepresentantes(List<PersonaHumana> representantes) {
        this.representantes = representantes;
    }
    public PersonaJuridica(String tipoD, String doc, String nom, String em, String tel) {
        super(tipoD, doc, nom, em, tel);
    }

}