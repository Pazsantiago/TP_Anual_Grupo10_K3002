package Sdonaciones.dominio.donante;

import Sdonaciones.dominio.donacion.Donacion;
import lombok.Data;

import java.util.List;

@Data
public class Donante {
    private List<Donacion> donaciones;
    private Persona persona;
    private List<MedioContacto> mediosDeContacto;

    public void agregarMedioContacto(MedioContacto contacto) {
        mediosDeContacto.add(contacto);
    }

    public MedioContacto obtenerContactoPredeterminado() {
        return mediosDeContacto.stream().filter(p -> p.isEsPredeterminado()).findFirst().orElse(
                mediosDeContacto.getLast()
        );
    }

    public void cambiarContactoPredeterminado(MedioContacto contactoPredeterminado) {
        mediosDeContacto.stream().filter(p -> p.isEsPredeterminado()).findFirst().ifPresent(antiguo -> antiguo.setEsPredeterminado(false));
        mediosDeContacto.stream().filter(p -> p.equals(contactoPredeterminado)).findFirst().ifPresent(nuevo -> nuevo.setEsPredeterminado(true));
        if (!mediosDeContacto.contains(contactoPredeterminado)) {
            mediosDeContacto.add(contactoPredeterminado);
        }
    }

}