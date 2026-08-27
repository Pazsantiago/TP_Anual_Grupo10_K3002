package Sdonaciones.dominio.donante;

import Sdonaciones.dominio.donacion.Donacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Donante {
    private List<Donacion> donaciones;
    private Persona persona;
    private List<MedioContacto> mediosDeContacto = new ArrayList<>();

    public void agregarMedioContacto(MedioContacto contacto) {
        mediosDeContacto.add(contacto);
    }

    public MedioContacto obtenerContactoPredeterminado() {
        return mediosDeContacto.stream().filter(p -> p.isEsPredeterminado()).findFirst().orElse(null);
    }

    public void cambiarContactoPredeterminado(MedioContacto contactoPredeterminado) {
        mediosDeContacto.stream().filter(p -> p.isEsPredeterminado()).findFirst().ifPresent(antiguo -> antiguo.setEsPredeterminado(false));
        mediosDeContacto.stream().filter(p -> p.equals(contactoPredeterminado)).findFirst().ifPresent(nuevo -> nuevo.setEsPredeterminado(true));
        if (!mediosDeContacto.contains(contactoPredeterminado)) {
            mediosDeContacto.add(contactoPredeterminado);
        }
    }

}