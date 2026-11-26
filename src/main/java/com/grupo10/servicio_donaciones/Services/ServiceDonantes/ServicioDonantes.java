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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Donante obtenerPorEmail(String email) {
        return repoDonantes.findByMediosDeContacto_CorreoElectronicoIgnoreCase(email).orElse(null);
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
        comunicador.enviarDonante(persona, restClientIncentivos);
        return persona;

    }

    @Transactional
    private List<Donante> revisarDonantesRepetidos(List<Donante> donantes) {
        List<Donante> donantesAProcesar = new ArrayList<>();

        for (Donante donanteCSV : donantes) {
            String correo = donanteCSV.getMediosDeContacto().get(0).getCorreoElectronico();
            Optional<Donante> existenteEnDB = repoDonantes.findByMediosDeContacto_CorreoElectronicoIgnoreCase(correo);

            if (existenteEnDB.isPresent()) {
                Donante donanteAActualizar = existenteEnDB.get();
                donanteAActualizar.setPersona(donanteCSV.getPersona());
                donanteAActualizar.setMediosDeContacto(donanteCSV.getMediosDeContacto());

                donantesAProcesar.add(donanteAActualizar); //UPDATE
            } else {
                donantesAProcesar.add(donanteCSV); //INSERT
            }

        }
        return donantesAProcesar;
    }

    @Transactional
    public void crearUsuarioKeycloak(Donante donante, String email) {
        //Crear cuenta

        String clave = "Tmp_" + UUID.randomUUID().toString().replace("-", "").substring(0, 10) + "!A9";
        keycloakAdminService.crearUsuarioEnKeycloak(
                (email != null && !email.isBlank()) ? email : donante.obtenerContactoPredeterminado().getCorreoElectronico(),
                clave,
                "ROLE_DONANTE"
        );

        //Avisar credencial a donante
        String nombre = (donante.getPersona() instanceof PersonaHumana ph)
                ? ph.getNombre()
                : ((PersonaJuridica) donante.getPersona()).getRazonSocial();
        comunicador.enviarCredenciales(
                clave,
                nombre,
                donante.obtenerContactoPredeterminado().getCorreoElectronico(),
                donante.obtenerContactoPredeterminado().getTelefono(),
                donante.obtenerContactoPredeterminado().getTipo(),
                TipoEvento.USUARIO_CREADO, restClientMensajes
        );
    }

    @Transactional
    public String importarCSV(MultipartFile archivo) {
        Integer cantDonantesAnteriores = repoDonantes.findAll().size();
        List<Donante> importados = revisarDonantesRepetidos(importadorCSV.importarCsv(archivo));

        if (!importados.isEmpty()) {
            //Guardarlos
            this.repoDonantes.saveAll(importados);

            for (Donante donante : importados) {
                crearUsuarioKeycloak(donante, null);
            }

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
        comunicador.enviarDonanteActualizado(donante, restClientIncentivos);
        return donante;
    }

    @Transactional
    public String deletePersona(Long id) {
        Donante donante = repoDonantes.findByIdWithDonaciones(id).orElse(null);
        if (donante != null) {
            donante.desvincularRelaciones();
            repoDonantes.delete(donante);
        }
        comunicador.avisarDonanteEliminado(id, restClientIncentivos);
        return "Donante borrado";
    }
}
