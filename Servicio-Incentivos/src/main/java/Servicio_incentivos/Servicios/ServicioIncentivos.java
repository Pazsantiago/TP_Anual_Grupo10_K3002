package Servicio_incentivos.Servicios;

import Servicio_incentivos.Repositorio.RepositorioPerfiles;
import Servicio_incentivos.dominio.DonacionImportada;
import Servicio_incentivos.dominio.Misiones.Insignia;
import Servicio_incentivos.dominio.Misiones.Mision;
import Servicio_incentivos.dominio.Misiones.ProgresoMision;
import Servicio_incentivos.dominio.PerfilDonante;

import java.util.List;

public class ServicioIncentivos {
    private final RepositorioPerfiles repo;

    public ServicioIncentivos() {
        this(new RepositorioPerfiles());
    }

    public ServicioIncentivos(RepositorioPerfiles repo) {
        this.repo = repo;
    }

    public void procesarNuevaDonacion(long donanteID, DonacionImportada donacion) {
        PerfilDonante perfilAsociado = obtenerPerfil(donanteID);
        if (perfilAsociado == null || donacion == null) {
            return;
        }

        perfilAsociado.sumarDonacion();
        perfilAsociado.verificarRachaDonaciones(donacion.getFechaDonacion());
        ProgresoMision progresoAsociado = perfilAsociado.getProgreso();
        progresoAsociado.actualizar(donacion);
        verificarYCompletarMision(perfilAsociado);
        //verificarSubidaCategoria(perfilAsociado);
    }

    public void procesarDonacionEntregada(long donanteID, DonacionImportada donacion) {
        procesarNuevaDonacion(donanteID, donacion);
    }

    public Mision getMisionActual(long donanteID) {
        PerfilDonante perfil = obtenerPerfil(donanteID);
        return perfil != null ? perfil.getMisionActual() : null;
    }

    public List<Insignia> getInsignias(long donanteId) {
        PerfilDonante perfil = obtenerPerfil(donanteId);
        return perfil != null ? perfil.getInsignias() : List.of();
    }

    public PerfilDonante getMetricas(long donanteID) {
        return obtenerPerfil(donanteID);
    }

    private void verificarYCompletarMision(PerfilDonante perfilDonante) {
        if (perfilDonante == null) {
            return;
        }
        perfilDonante.misionCompletada();
    }

    /*private void verificarSubidaCategoria(PerfilDonante perfilDonante) {
        if (perfilDonante == null) {
            return;
        }
        if (perfilDonante.getPorcentajeProgreso() >= 100) {
            perfilDonante.subirCategoria();
        }
    }*/

    private PerfilDonante obtenerPerfil(long donanteID) {
        return repo != null ? repo.getxId(donanteID) : null;
    }
}

