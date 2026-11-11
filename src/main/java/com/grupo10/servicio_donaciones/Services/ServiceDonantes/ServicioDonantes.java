package com.grupo10.servicio_donaciones.Services.ServiceDonantes;

import com.grupo10.servicio_donaciones.comunicador.Comunicador;
import com.grupo10.servicio_donaciones.donaciones.dominio.Importador.Importador;
import com.grupo10.servicio_donaciones.donaciones.dominio.categoria.Categoria;
import com.grupo10.servicio_donaciones.donaciones.dominio.donante.Donante;
import com.grupo10.servicio_donaciones.donaciones.dominio.donante.PersonaHumana;
import com.grupo10.servicio_donaciones.donaciones.dominio.donante.PersonaJuridica;
import com.grupo10.servicio_donaciones.donaciones.mensajes.TipoEvento;
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

    @Transactional
    public void setearCategorias(Donante persona) {
        persona.getDonaciones().forEach(d -> {
            d.getBienesDeEntrada().forEach(bien -> {
                Categoria categoria = repoCategorias.getReferenceById(bien.getSubcategoria().getCategoria().getId());
                bien.getSubcategoria().setCategoria(categoria);
            });
        });
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void revisarPresenciaEnPlataforma() {
        LocalDateTime limite = LocalDateTime.now().minusDays(20);
        repoDonantes.findAll().forEach(donante -> {
            if (donante.getUltimaInteraccion().isBefore(limite)) {
                String nombre = (donante.getPersona() instanceof PersonaHumana ph)
                        ? ph.getNombre()
                        : ((PersonaJuridica) donante.getPersona()).getRazonSocial();
                comunicador.comunicarMensaje(
                        nombre,
                        donante.obtenerContactoPredeterminado().getCorreoElectronico(),
                        donante.obtenerContactoPredeterminado().getTelefono(),
                        donante.obtenerContactoPredeterminado().getTipo(),
                        TipoEvento.DONANTE_INACTIVO, restClientMensajes);
            }
        });
    }

    @Transactional
    public Donante createPersona(Donante persona) {
        persona.setUltimaInteraccion(LocalDateTime.now());
        repoDonantes.save(persona);
        //comunicador.enviarDonante(persona, restClientIncentivos);
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
        Donante donante = repoDonantes.findById(id).orElse(null);
        donante.setPersona(updateDonante.getPersona());
        donante.setUltimaInteraccion(LocalDateTime.now());
        donante.setMediosDeContacto(updateDonante.getMediosDeContacto());
        setearCategorias(donante);
        //comunicador.enviarDonanteActualizado(donante, restClientIncentivos);
        return donante;
    }

    @Transactional
    public String deletePersona(Long id) {
        Donante donante = repoDonantes.findByIdWithDonaciones(id).orElse(null);
        if (donante != null) {
            donante.desvincularRelaciones();
            repoDonantes.delete(donante);
        }
        //comunicador.avisarDonanteEliminado(id, restClientIncentivos);
        return "Donante borrado";
    }
}
