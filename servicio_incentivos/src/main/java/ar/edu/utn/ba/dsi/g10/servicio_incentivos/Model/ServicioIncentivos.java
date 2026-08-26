package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Insignia;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.ProgresoMision;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Repository.RepositorioPerfiles;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioIncentivos {
    private final RepositorioPerfiles repo;
    /* 
    public ServicioIncentivos() {
        this(new RepositorioPerfiles());
    }
    */
    public ServicioIncentivos(RepositorioPerfiles repo) {
        this.repo = repo;
    }

    public void procesarNuevaDonacion(long donanteID, DonacionImportada donacion) {
        PerfilDonante perfilAsociado = obtenerPerfil(donanteID);
        if (perfilAsociado == null || donacion == null) {
            return;
        }

        perfilAsociado.sumarDonacion();
        //perfilAsociado.verificarRachaDonaciones(donacion.getFechaDonacion());
        //ProgresoMision progresoAsociado = perfilAsociado.getProgreso();
        //progresoAsociado.actualizar(donacion);
        //verificarYCompletarMision(perfilAsociado);
        
        
        
        //verificarSubidaCategoria(perfilAsociado);
        //  TODO: cambios realizados a partir de acá, revisar
        // Evalúa racha mensual del donante
        perfilAsociado.verificarRachaDonaciones(donacion.getFechaDonacion());

        // Actualiza el progreso de la misión actual con la nueva donación
        ProgresoMision progresoAsociado = perfilAsociado.getProgreso();
        if (progresoAsociado != null) {
            progresoAsociado.actualizar(donacion);
        }

        // Otorga insignia si completó la misión
        verificarYCompletarMision(perfilAsociado);

        // Promueve de categoría si alcanzó el 100% de progreso
        verificarSubidaCategoria(perfilAsociado);

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
        return perfil != null ? perfil.getInsignias() : null;
    }

    public PerfilDonante getMetricas(long donanteID) {
        return obtenerPerfil(donanteID);
    }

    private void verificarYCompletarMision(PerfilDonante perfilDonante) {
        if (perfilDonante == null) {
            return;
        }
        perfilDonante.misionCompletada(); // 2. ¿por que se saca esto?

        //2. ¿porque se reemplaza por esto?
        if (perfilDonante.getPorcentajeProgreso() >= 100) {
            perfilDonante.subirCategoria();
        }
    }

    /*private void verificarSubidaCategoria(PerfilDonante perfilDonante) {
        if (perfilDonante == null) {
            return;
        }
        if (perfilDonante.getPorcentajeProgreso() >= 100) {
            perfilDonante.subirCategoria();
        }
    }*/

    private void verificarSubidaCategoria(PerfilDonante perfilDonante) {
        if (perfilDonante != null && perfilDonante.getPorcentajeProgreso() >= 100.0) {
            perfilDonante.subirCategoria(); // Eleva la categoría[cite: 12, 22]
        }
    }

    private PerfilDonante obtenerPerfil(long donanteID) {
    
        //CODIGO PRUEBA 
    /*   // Si es la primera vez que aparece este donante, lo creamos e incluimos
    if (perfil == null) {
        perfil = new PerfilDonante(donanteID);
        repo.guardar(perfil);
        return perfil;
    }    
    // FIN CODIGO PRUEBA 
    */
        return repo != null ? repo.getxId(donanteID) : null;
    }
}
