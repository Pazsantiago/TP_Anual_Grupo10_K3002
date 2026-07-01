package Servicio_incentivos.Servicios;


import Servicio_incentivos.Repositorio.RepositorioPerfiles;
import Servicio_incentivos.dominio.DonacionImportada;
import Servicio_incentivos.dominio.Misiones.Insignia;
import Servicio_incentivos.dominio.Misiones.Mision;
import Servicio_incentivos.dominio.Misiones.ProgresoMision;
import Servicio_incentivos.dominio.PerfilDonante;

import java.util.List;

public class ServicioIncentivos {
    private RepositorioPerfiles repo;

    public void procesarNuevaDonacion(long donanteID, DonacionImportada donacion) {
       PerfilDonante perfilAsociado = repo.getxId(donanteID);
       //le pido a perfildonante su progreso (getprogreso en Perfildonante)
        ProgresoMision progresoAsociado = perfilAsociado.getProgreso();
       //me fijo el progreso y le doy la donacion para que calcule el progreso
        progresoAsociado.actualizar(donacion);  //en esta linea si ya cumple con el progreso se marca como completada
        // le digo a perfilasociado que sume su progreso y a su vez que vea si completo la mision para darse la insignia
        perfilAsociado.misionCompletada();
    }



    public void procesarDonacionEntregada(long donanteID) {}
    public Mision getMisionActual(long donanteID) {
        return repo.getxId(donanteID).getMisionActual();
    }
    public List<Insignia> getInsignias(long donanteId){ return repo.getxId(donanteId).getInsignias();}
    public PerfilDonante getMetricas(long donanteID) { return repo.getxId(donanteID); }
    private void verificarYCompletarMision (PerfilDonante PerfilDonante) {
    }
    private void verificarSubidaCategoria (PerfilDonante  PerfilDonante){
    }
}

