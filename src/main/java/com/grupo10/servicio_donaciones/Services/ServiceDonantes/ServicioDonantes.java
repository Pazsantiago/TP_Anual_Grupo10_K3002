package com.grupo10.servicio_donaciones.Services.ServiceDonantes;

import com.grupo10.servicio_donaciones.Services.KeyclockService.KeycloakAdminService;
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
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private final KeycloakAdminService keycloakAdminService;

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

    private List<Donante> revisarDonantesRepetidos(List<Donante> donantes) {
        List<String> correosCSV = donantes.stream()
                .map(d -> d.getMediosDeContacto().get(0).getCorreoElectronico())
                .collect(Collectors.toList());

        List<Donante> existentesEnDB = repoDonantes.findByMediosDeContactoCorreoElectronicoIn(correosCSV);
        Set<String> correosExistentes = existentesEnDB.stream()
                .map(d -> d.getMediosDeContacto().get(0).getCorreoElectronico().toLowerCase())
                .collect(Collectors.toSet());

        return donantes.stream()
                .filter(d -> !correosExistentes.contains(
                        d.getMediosDeContacto().get(0).getCorreoElectronico().toLowerCase()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public String importarCSV(MultipartFile archivo) {
        Integer cantDonantesAnteriores = repoDonantes.findAll().size();
        List<Donante> importados = revisarDonantesRepetidos(importadorCSV.importarCsv(archivo));

        if (!importados.isEmpty()) {
            //Guardarlos
            this.repoDonantes.saveAll(importados);
            //Crear cuenta
            for (Donante donante : importados) {
                keycloakAdminService.crearUsuarioEnKeycloak(
                        donante.obtenerContactoPredeterminado().getCorreoElectronico(),
                        "Tmp_" + UUID.randomUUID().toString().replace("-", "").substring(0, 10) + "!A9",
                        "ROL_DONANTE"
                );
            }
            //Avisar credenciales a cada donante
            importados.forEach(donante -> {
                String nombre = (donante.getPersona() instanceof PersonaHumana ph)
                        ? ph.getNombre()
                        : ((PersonaJuridica) donante.getPersona()).getRazonSocial();
                comunicador.comunicarMensaje(
                        nombre,
                        donante.obtenerContactoPredeterminado().getCorreoElectronico(),
                        donante.obtenerContactoPredeterminado().getTelefono(),
                        donante.obtenerContactoPredeterminado().getTipo(),
                        TipoEvento.USUARIO_CREADO, restClientMensajes
                );
            });

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
