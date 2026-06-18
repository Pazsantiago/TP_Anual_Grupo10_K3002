package Servicio_incentivos.dominio.CategoriasDonante;


import Servicio_incentivos.dominio.Misiones.Mision;

import java.util.List;

public class CategoriaDonante {
    public CategoriaDonante categoriaSiguiente;
    public List<Mision> Misiones;
    public CategoriaDonante siguienteCategoria() {
        return this.categoriaSiguiente;
    }
}

