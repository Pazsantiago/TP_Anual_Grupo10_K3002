package Sdonaciones.dominio.donacion;

import Sdonaciones.dominio.bien.Bien;
import Sdonaciones.dominio.bien.Estado;
import Sdonaciones.dominio.categoria.Subcategoria;

import java.util.List;

public class DonacionSegmentada {
    private Bien bien;
    private CambioEstado estadoActual;
    private List<CambioEstado> estadoHistorico;

    public Subcategoria getSubcategoria(){
        return bien.getSubcategoria();
    }
}
