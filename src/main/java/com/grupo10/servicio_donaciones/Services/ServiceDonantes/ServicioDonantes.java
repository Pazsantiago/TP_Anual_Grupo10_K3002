package com.grupo10.servicio_donaciones.Services.ServiceDonantes;

import com.grupo10.servicio_donaciones.Sdonaciones.Importador.Importador;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.categoria.Categoria;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante.Donante;
import com.grupo10.servicio_donaciones.Sdonaciones.dominio.mensajes.TipoEvento;
import com.grupo10.servicio_donaciones.comunicador.Comunicador;
import com.grupo10.servicio_donaciones.repositorios.RepoCategorias;
import com.grupo10.servicio_donaciones.repositorios.RepoDonaciones;
import com.grupo10.servicio_donaciones.repositorios.RepoDonantes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServicioDonantes {
    private final Importador importadorCSV = Importador.GetInstance();
    private final RepoDonantes repoDonantes;
    private final RepoDonaciones repoDonaciones;
    private final RepoCategorias repoCategorias;
    @Qualifier("restClientIncentivos")
    private final RestClient restClientIncentivos;
    @Qualifier("restClientMensajes")
    private final RestClient restClientMensajes;
    private final Comunicador comunicador;

    public List<Donante> getAllPersonas() {
        return repoDonantes.findAll();
    }


    public Donante obtenerPersonaPorDocumento(String tipoD, String doc) {
        return repoDonantes.findByDoc(tipoD, doc).orElse(null);
    }

    public Donante obtenerPorId(Long id) {
        return repoDonantes.findById(id).orElse(null);
    }

    public void setearCategorias(Donante persona) {
        persona.getDonaciones().forEach(d -> {
            d.getBienesDeEntrada().forEach(bien -> {
                Categoria categoria = repoCategorias.findById(bien.getSubcategoria().getCategoria().getId()).orElse(null);
                bien.getSubcategoria().setCategoria(categoria);
            });
        });
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void revisarPresenciaEnPlataforma() {
        LocalDateTime limite = LocalDateTime.now().minusDays(20);
        repoDonantes.findAll().forEach(donante -> {
            if (donante.getUltimaInteraccion().isBefore(limite)) {
                comunicador.comunicarMensaje(donante.obtenerContactoPredeterminado().getCorreoElectronico(),
                        donante.obtenerContactoPredeterminado().getTipo(),
                        donante.obtenerContactoPredeterminado().getTelefono(),
                        TipoEvento.DONANTE_INACTIVO, restClientMensajes);
            }
        });
    }

    @Transactional
    public Donante createPersona(Donante persona) {
        if (persona.getDonaciones() != null) {
            persona.setearRelaciones();
            persona.getDonaciones().forEach(d -> {
                d.setearRelaciones();
                repoDonaciones.save(d);
            });
        }

        setearCategorias(persona);
        repoDonantes.save(persona);
        comunicador.enviarDonante(persona, "/perfiles", restClientIncentivos);
        return persona;

    }

    @Transactional
    public String importarCSV(String rutaArchivo) {
        importadorCSV.setRepositorioDonadores(repoDonantes);
        Integer cantDonantesAnteriores = repoDonantes.findAll().size();
        boolean importado = importadorCSV.importarCsv(rutaArchivo);

        if (importado) {
            return "Archivo importado correctamente" +
                    " - Cantidad de registros creados: " + (repoDonantes.count() - cantDonantesAnteriores);
        }

        return "No se pudo importar el archivo";
    }

    @Transactional
    public Donante updateDonante(Long id, Donante updateDonante) {
        Donante oldDonante = repoDonantes.findById(id).orElse(null);
        oldDonante.setId(id);
        oldDonante.setPersona(updateDonante.getPersona());
        oldDonante.setUltimaInteraccion(updateDonante.getUltimaInteraccion());
        oldDonante.setMediosDeContacto(updateDonante.getMediosDeContacto());
        oldDonante.getDonaciones().forEach(d -> {
            repoDonaciones.deleteById(d.getId());
        });
        oldDonante.borrarDonaciones();
        oldDonante.setDonaciones(updateDonante.getDonaciones());
        oldDonante.setearRelaciones();
        oldDonante.getDonaciones().forEach(d -> {
            d.setearRelaciones();
            repoDonaciones.save(d);
        });
        setearCategorias(oldDonante);
        repoDonantes.save(oldDonante);
        comunicador.enviarDonanteActualizado(updateDonante, "/perfiles/{idDonante}" + updateDonante.getId(), restClientIncentivos);
        return oldDonante;
    }

    @Transactional
    public String deletePersona(Long id) {
        repoDonantes.deleteById(id);
        repoDonantes.findByIdWithDonaciones(id).orElse(null).getDonaciones().forEach(d -> {
            if (d.getDonante().getId().equals(id)) {
                d.setDonante(null);
                repoDonaciones.save(d);
            }
        });
        comunicador.avisarDonanteEliminado("/perfiles/{idDonante}" + id, restClientIncentivos);
        return "Donante borrado";
    }
}
