package Servicio_incentivos.Servicios;


import Servicio_incentivos.Repositorio.RepositorioPerfiles;
import Servicio_incentivos.dominio.Misiones.Insignia;
import Servicio_incentivos.dominio.PerfilDonante;

import java.util.List;

public class ServicioIncentivos {
    private RepositorioPerfiles repo;
    public void procesarNuevaDonacion(long donanteID) {}
    public void procesarDonacionEntregada(long donanteID) {}
    public void getMisionActual(long donanteID) {}
    public List<Insignia> getInsignias(long donanteId){ return repo.getxId(donanteId).getInsignias();}
    public PerfilDonante getMetricas(long donanteID) { return repo.getxId(donanteID); }
    private void verificarYCompletarMision (PerfilDonante PerfilDonante) {}
    private void verificarSubidaCategoria (PerfilDonante  PerfilDonante){}
}

