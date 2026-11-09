package org.example.donacionesfrontend.dominio.bien;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BienConEstado extends Bien {
    private Estado estado;

//    public BienConEstado(String descripcion, Subcategoria subcategoria, URL foto, UnidadMedida unidad, Integer cantidad, Estado estado) {
//        super(descripcion, subcategoria, foto, unidad, cantidad);
//        this.estado = estado;
//    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}

