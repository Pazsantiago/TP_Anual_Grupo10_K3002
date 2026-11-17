package com.grupo10.servicio_donaciones.donaciones.dominio.Importador;

import com.grupo10.servicio_donaciones.donaciones.dominio.donante.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Importador {
    private static Importador instancia = null;

    private Importador() {
    }

    public static Importador GetInstance() {
        if (instancia == null)
            instancia = new Importador();
        return instancia;
    }

    @Transactional
    public List<Donante> importarCsv(MultipartFile archivo) {
        Map<String, Donante> donantesUnicosCSV = new LinkedHashMap<>();
        boolean primeraLinea = true;

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(archivo.getInputStream(), StandardCharsets.UTF_8))) {
            String[] fila;
            while ((fila = csvReader.readNext()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String correo = fila[4].toLowerCase().trim();
                Donante donante = setearDonante(fila);
                donantesUnicosCSV.put(correo, donante);
            }

        } catch (IOException | CsvValidationException e) {
            return List.of();
        }
        return new ArrayList<>(donantesUnicosCSV.values());
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
            PersonaJuridica nuevo = new PersonaJuridica(doc, null, fila[3], null, null, new ArrayList<>());
            donante.setPersona(nuevo);
        }
        return donante;
    }

}