package Servicio_donaciones.dominio.donacion;

import Servicio_donaciones.dominio.bien.Bien;
import Servicio_donaciones.dominio.categoria.Subcategoria;

import java.util.List;

public class DonacionSegmentada {
    private Bien bien;
    private CambioEstado estadoActual;
    private List<CambioEstado> estadoHistorico;

    public DonacionSegmentada(Bien bien, CambioEstado estadoActual){
        this.bien = bien;
        this.estadoActual = estadoActual;
    }

    // modificar: getsubcategoria es propio del bien, bien.getsubcategoria...
    public Subcategoria getSubcategoria(){
        return bien.getSubcategoria();
    }

    public void cambiarEstado(CambioEstado estadoNuevo){
        estadoHistorico.add(this.estadoActual);
        this.estadoActual = estadoNuevo;
    }

    public Bien getBien(){
        return this.bien;
    }

    public CambioEstado getEstado(){
        return this.estadoActual;
    }

    public List<CambioEstado> getEstadoHistorico(){
        return this.estadoHistorico;
    }
}
