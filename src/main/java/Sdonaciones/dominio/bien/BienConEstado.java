package Sdonaciones.dominio.bien;


import Sdonaciones.dominio.categoria.Subcategoria;

import java.net.URL;

public class BienConEstado extends Bien{
    public BienConEstado(String descripcion, Subcategoria subcategoria, Integer cantidad, Estado estado, URL foto) {
        super(descripcion, subcategoria, cantidad, estado, foto);
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    private Estado estado;
}
