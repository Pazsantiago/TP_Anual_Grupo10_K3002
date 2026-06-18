package Servicio_donaciones.dominio.bien;

import Servicio_donaciones.dominio.categoria.Subcategoria;

import java.net.URL;


public class BienConEstado extends Bien {

    private final Estado estado;

    public BienConEstado(String descripcion, Subcategoria subcategoria,
                         int cantidad, Estado estado, URL foto) {
        super(descripcion, subcategoria, cantidad, null, foto);
        this.estado = estado;
    }

}
