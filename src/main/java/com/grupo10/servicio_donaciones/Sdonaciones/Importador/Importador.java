package com.grupo10.servicio_donaciones.Sdonaciones.Importador;

import com.grupo10.servicio_donaciones.Sdonaciones.dominio.donante.*;
import com.grupo10.servicio_donaciones.repositorios.RepoDonantes;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Importador {
    private static Importador instancia = null;
    private RepoDonantes repositorioDonadores = null;


    private Importador() {
    }

    public static Importador GetInstance() {
        if (instancia == null)
            instancia = new Importador();
        return instancia;
    }

    public void setRepositorioDonadores(RepoDonantes repo) {
        this.repositorioDonadores = repo;
    }

    @Transactional
    public boolean importarCsv(String ruta_archivo) {
        boolean first = true;
        try (CSVReader csvReader = new CSVReader(new FileReader(ruta_archivo))) {
            String[] fila;
            List<Donante> donantes = new ArrayList<>();
            while ((fila = csvReader.readNext()) != null) {
                if (first) {
                    first = false;
                    continue;
                }
                donantes.add(controlarDonanteEnLista(fila));
            }
            repositorioDonadores.saveAll(donantes);
            return true;

        } catch (IOException | CsvValidationException e) {
            return false;
        }
    }

    @Transactional
    public Donante controlarDonanteEnLista(String[] fila) {
        // TODO , cambiar el tema de que sea por un find by
        Optional<Donante> donanteExistente = repositorioDonadores.findByMediosDeContactoCorreoElectronico(fila[4]);
        //En caso de que un registro
        //ya exista (esto quiere decir que el correo electrónico ya se encuentra registrado en el servicio) se deberá
        //actualizar su información --> Esto es para el csv.
        Donante donanteAGuardar;
        if (donanteExistente.isPresent()) {
            donanteAGuardar = setearDonante(fila);
            donanteAGuardar.setId(donanteExistente.get().getId());
        } else {
            donanteAGuardar = setearDonante(fila);
        }
        return donanteAGuardar;
    }
    
    public Donante setearDonante(String[] fila) {
        Donante donante = new Donante();
        MedioContacto medioDeContacto = new MedioContacto();
        medioDeContacto.setCorreoElectronico(fila[4]);
        medioDeContacto.setTelefono(fila[5]);
        medioDeContacto.setEsPredeterminado(true);
        donante.agregarMedioContacto(medioDeContacto);
        Documento doc = new Documento();
        doc.setDocumento(fila[2]);
        doc.setTipoDocumento(fila[1]);
        if (fila[0].equalsIgnoreCase("HUMANA")) {
            PersonaHumana nuevo = new PersonaHumana();
            nuevo.setNombre(fila[3]);
            nuevo.setDocumento(doc);
            donante.setPersona(nuevo);
        } else {
            PersonaJuridica nuevo = new PersonaJuridica();
            nuevo.setRazonSocial(fila[3]);
            nuevo.setDocumento(doc);
            donante.setPersona(nuevo);
        }
        return donante;
    }

}