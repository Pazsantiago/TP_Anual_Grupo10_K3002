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

    public boolean importarCsv(String ruta_archivo) {
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
            return true;

        } catch (IOException | CsvValidationException e) {
            return false;
        }
    }


    public void controlarDonanteEnLista(String[] fila) {
        Optional<Donante> donanteExistente = repositorioDonadores.getDonantes().stream().filter(donante -> donante.obtenerContactoPredeterminado().getCorreoElectronico().equals(fila[4])).findFirst();
        //En caso de que un registro
        //ya exista (esto quiere decir que el correo electrónico ya se encuentra registrado en el servicio) se deberá
        //actualizar su información --> Esto es para el csv.
        if (donanteExistente.isPresent()) {
            Integer i = repositorioDonadores.getDonantes().indexOf(donanteExistente.get());
            Donante donanteActualizado = setearDonante(fila);
            MedioContacto nuevoPredeterminado = donanteActualizado.obtenerContactoPredeterminado();
            repositorioDonadores.getDonantes().get(i).getMediosDeContacto().forEach(contacto -> {
                donanteActualizado.agregarMedioContacto(contacto);
            });
            donanteActualizado.cambiarContactoPredeterminado(nuevoPredeterminado);
            repositorioDonadores.getDonantes().set(i, donanteActualizado);
        } else {
            repositorioDonadores.getDonantes().add(setearDonante(fila));
        }
    }


    public Donante setearDonante(String[] fila) {
        Donante donante = new Donante();
        donante.agregarMedioContacto(new MedioContacto(null, fila[4], fila[5], true));
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