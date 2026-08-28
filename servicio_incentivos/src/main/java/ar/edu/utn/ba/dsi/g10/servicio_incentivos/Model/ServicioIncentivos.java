package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Insignia;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.ProgresoMision;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Ranking.PosicionRanking;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Repository.RepositorioPerfiles;
<<<<<<< HEAD
=======
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
>>>>>>> d1de2abbcb740c34e73ef770a5a6216c902a9c8d

@Service
public class ServicioIncentivos {
    private final RepositorioPerfiles repo;
   
    public ServicioIncentivos(RepositorioPerfiles repo) {
        this.repo = repo;
    }

    public void procesarNuevaDonacion(long donanteID, DonacionImportada donacion) {
        PerfilDonante perfilAsociado = obtenerPerfil(donanteID);
        if (perfilAsociado == null || donacion == null) {
            return;
        }

        perfilAsociado.sumarDonacion();
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

    public void enviarInsigniaAN8N(PerfilDonante perfil) {

    try {

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> body = new HashMap<>();

        Insignia ultimaInsignia =
                perfil.getInsignias().get(perfil.getInsignias().size() - 1);

        body.put("user", perfil.getDonanteID());
        body.put("badge", ultimaInsignia.getNombre());
        body.put("description", ultimaInsignia.getDescripcion());

        restTemplate.postForEntity(
                "http://localhost:5678/webhook/Insignias",
                body,
                String.class
        );

    } catch (Exception e) {
        System.out.println("Error enviando evento a n8n: " + e.getMessage());
    }
}

    private void verificarYCompletarMision(PerfilDonante perfilDonante) {

    if (perfilDonante == null) {
        return;
    }

    if (perfilDonante.misionCompletada()) {

        repo.actualizarRankingPerfil(perfilDonante);

        enviarInsigniaAN8N(perfilDonante);
    }

    if (perfilDonante.getPorcentajeProgreso() >= 100) {
        perfilDonante.subirCategoria();
    }
}

    public List<PosicionRanking> getRanking(int tamaño) {
        return repo.obtenerRankingMensual(tamaño);
    }


    private void verificarSubidaCategoria(PerfilDonante perfilDonante) {
        if (perfilDonante != null && perfilDonante.getPorcentajeProgreso() >= 100.0) {
            perfilDonante.subirCategoria(); // Eleva la categoría[cite: 12, 22]
        }
    }

    private PerfilDonante obtenerPerfil(long donanteID) {
        return repo != null ? repo.getxId(donanteID) : null;
    }



    public void asignarNuevaMision(long donanteID, Mision nuevaMision) {
        PerfilDonante perfil = obtenerPerfil(donanteID);
        if (perfil != null && nuevaMision != null) {
            perfil.setMisionActual(nuevaMision);

            ProgresoMision nuevoProgreso = new ProgresoMision();
            nuevoProgreso.setMisionAsociada(nuevaMision);
            nuevoProgreso.setProgresoActual(0.0);
            perfil.getHistorialMisiones().add(nuevoProgreso);
        }
    }
@Scheduled(cron = "0 59 23 L * ?")
public void reiniciarRanking() { // 👈 CAMBIA EL CORCHETE/LLAVE POR ESTA LLAVE DE APERTURA
    repo.reiniciarRankingMensual();
    System.out.println("Se ha reinventado el ranking mensual exitosamente.");
}
}
