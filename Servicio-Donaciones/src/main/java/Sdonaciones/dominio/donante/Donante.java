package Sdonaciones.dominio.donante;

import java.util.List;

public class Donante{
    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    private TipoPersona tipoPersona;

    public List<MedioContacto> getMediosDeContacto() {
        return mediosDeContacto;
    }

    public void agregarMediosDeContacto(MedioContacto medioDeContacto) {
        this.mediosDeContacto.add(medioDeContacto);
    }

    private List<MedioContacto> mediosDeContacto;


    public MedioContacto obtenerContactoPredeterminado(){
        return mediosDeContacto.getLast();
    }

    public void cambiarContactoPredeterminado(MedioContacto contacto){

    }

}