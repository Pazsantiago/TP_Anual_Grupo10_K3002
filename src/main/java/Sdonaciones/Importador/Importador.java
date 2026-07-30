package Sdonaciones.Importador;

import Sdonaciones.dominio.donante.*;
import Sdonaciones.repositorios.RepoDonantes;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
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

    public void importarCsv(String ruta_archivo) {
        boolean first = true;
        try (CSVReader csvReader = new CSVReader(new FileReader(ruta_archivo))) {
            String[] fila;
            while ((fila = csvReader.readNext()) != null) {
                if (first) {
                    first = false;
                    continue;
                }
                controlarDonanteEnLista(fila);

            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

    }

    public void controlarDonanteEnLista(String[] fila) {
        Optional<Donante> donanteExistente = repositorioDonadores.getDonantes().stream().filter(donante -> donante.obtenerContactoPredeterminado().getCorreoElectronico().equals(fila[4])).findFirst();
        if (donanteExistente.isPresent()) {
            Integer i = repositorioDonadores.getDonantes().indexOf(donanteExistente.get());
            repositorioDonadores.getDonantes().set(i, setearDonante(fila));
        } else {
            repositorioDonadores.getDonantes().add(setearDonante(fila));
        }
    }


    public Donante setearDonante(String[] fila) {
        Donante donante = new Donante();
        donante.agregarMedioContacto(new MedioContacto(null, fila[4], fila[5], false));

        if (fila[0].equalsIgnoreCase("HUMANA")) {
            PersonaHumana nuevo = new PersonaHumana(fila[3], null, null);
            nuevo.setDocumento(new Documento(fila[1], fila[2]));
            donante.setPersona(nuevo);
        } else {
            PersonaJuridica nuevo = new PersonaJuridica(fila[3], null, null, null);
            nuevo.setDocumento(new Documento(fila[1], fila[2]));
            donante.setPersona(nuevo);
        }
        return donante;
    }

}