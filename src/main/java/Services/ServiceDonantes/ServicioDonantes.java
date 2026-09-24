package Services.ServiceDonantes;

import Sdonaciones.Importador.Importador;
import Sdonaciones.comunicador.Comunicador;
import Sdonaciones.dominio.donante.Donante;
import Sdonaciones.dominio.mensajes.TipoEvento;
import Sdonaciones.repositorios.RepoDonaciones;
import Sdonaciones.repositorios.RepoDonantes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServicioDonantes {
    private final Importador importadorCSV = Importador.GetInstance();
    private final RepoDonantes repoDonantes;
    private final RepoDonaciones repoDonaciones;
    private final RestClient restClientIncentivos;
    private final RestClient restClientMensajes;
    private final Comunicador comunicador;

    public ServicioDonantes(RepoDonantes repoDonantes, @Qualifier("restClientIncentivos") RestClient restClientIncentivos,
                            @Qualifier("restClientMensajes") RestClient restClientMensajes, Comunicador comunicador, RepoDonaciones repoDonaciones) {
        this.repoDonantes = repoDonantes;
        this.restClientIncentivos = restClientIncentivos;
        this.restClientMensajes = restClientMensajes;
        this.comunicador = comunicador;
        this.repoDonaciones = repoDonaciones;
    }


    public List<Donante> getAllPersonas() {
        return repoDonantes.listarTodos();
    }


    public Donante obtenerPersonaPorDocumento(String tipoD, String doc) {
        return repoDonantes.buscarPorDocumento(tipoD, doc);
    }

    public Donante obtenerPorId(Long id) {
        return repoDonantes.buscarPorId(id);
    }


    @Scheduled(cron = "0 0 0 * * *")
    public void revisarPresenciaEnPlataforma() {
        LocalDateTime limite = LocalDateTime.now().minusDays(20);
        repoDonantes.getDonantes().forEach(donante -> {
            if (donante.getUltimaInteraccion().isBefore(limite)) {
                comunicador.comunicarMensaje(donante.obtenerContactoPredeterminado().getCorreoElectronico(),
                        donante.obtenerContactoPredeterminado().getTipo(),
                        donante.obtenerContactoPredeterminado().getTelefono(),
                        TipoEvento.DONANTE_INACTIVO, restClientMensajes);
            }
        });
    }

    public Donante createPersona(Donante persona) {
        repoDonantes.guardar(persona);
        if (persona.getDonaciones() != null) {
            persona.getDonaciones().forEach(d -> {
                d.segmentarse(repoDonaciones.getUltimoIdDonacionSegmentada());
                repoDonaciones.guardar(d);
            });
        }
        //comunicador.enviarDonante(persona, "/donantes", restClientIncentivos);
        return persona;

    }

    public String importarCSV(String rutaArchivo) {
        importadorCSV.setRepositorioDonadores(repoDonantes);
        Integer cantDonantesAnteriores = repoDonantes.getDonantes().size();
        boolean importado = importadorCSV.importarCsv(rutaArchivo);

        if (importado) {
            return "Archivo importado correctamente" +
                    " - Cantidad de registros creados: " + (repoDonantes.getDonantes().size() - cantDonantesAnteriores);
        }

        return "No se pudo importar el archivo";
    }


    public Donante updateDonante(Long id, Donante updateDonante) {
        Donante oldDonante = repoDonantes.buscarPorId(id);
        oldDonante.setPersona(updateDonante.getPersona());
        oldDonante.setUltimaInteraccion(updateDonante.getUltimaInteraccion());
        oldDonante.setMediosDeContacto(updateDonante.getMediosDeContacto());
        repoDonantes.actualizarDonante(updateDonante);
        oldDonante.getDonaciones().forEach(d -> {
            repoDonaciones.borrarDonacion(d.getId());
        });
        oldDonante.setDonaciones(updateDonante.getDonaciones());
        oldDonante.getDonaciones().forEach(d -> {
            d.segmentarse(repoDonaciones.getUltimoIdDonacionSegmentada());
            repoDonaciones.guardar(d);
        });
        //comunicador.enviarDonanteActualizado(updateDonante, "/donantes/{idDonante}" + updateDonante.getId(), restClientIncentivos);
        return updateDonante;
    }


    public String deletePersona(Long id) {
        repoDonantes.eliminarDonante(id);
        repoDonaciones.getDonaciones().forEach(d -> {
            if (d.getDonante().getId().equals(id)) {
                d.setDonante(null);
                repoDonaciones.actualizarDonacion(d);
            }
        });
        //comunicador.avisarDonanteEliminado("/donantes/{idDonante}" + id, restClientIncentivos);
        return "Donante borrado";
    }
}
