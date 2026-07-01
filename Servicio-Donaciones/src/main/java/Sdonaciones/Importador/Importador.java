package Sdonaciones.Importador;

import Sdonaciones.dominio.donante.Donante;
import Sdonaciones.repositorios.*;
import Sdonaciones.dominio.donante.MedioContacto;
import Sdonaciones.dominio.donante.PersonaHumana;
import Sdonaciones.dominio.donante.PersonaJuridica;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;


public class Importador {
    private static Importador instancia = null;
    private RepositorioDonantes repositorioDonadores = null;


    private Importador(){}
    public static Importador GetInstance(){
        if(instancia == null)
            instancia = new Importador();
        return instancia;
    }

    public void setRepositorioDonadores(RepositorioDonantes repo){
        this.repositorioDonadores = repo;
    }

    public void importarCsv(String ruta_archivo) {
        boolean first = true;
        try (CSVReader csvReader = new CSVReader(new FileReader(ruta_archivo))) {
            String[] fila;
            while ((fila = csvReader.readNext()) != null) {
                if (first){
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
        Optional<Donante> donanteExistente = repositorioDonadores.listarTodos().stream().filter(donante -> donante.obtenerContactoPredeterminado().getCorreoElectronico().equals(fila[4])).findFirst();
        if (donanteExistente.isPresent()) {
            int i = repositorioDonadores.listarTodos().indexOf(donanteExistente.get());
            repositorioDonadores.listarTodos().set(i, setearDonante(fila));
        } else {
            repositorioDonadores.listarTodos().add(setearDonante(fila));
        }
    }


    public Donante setearDonante(String[] fila) {
        Donante donante = new Donante();
        donante.agregarMediosDeContacto(new MedioContacto(fila[4], fila[5], false));
        if (fila[0].equalsIgnoreCase("HUMANA")) {
            donante.setTipoPersona(new PersonaHumana(fila[1], fila[2], fila[3]));

        } else {
            donante.setTipoPersona(new PersonaJuridica(fila[1], fila[2], fila[3]));
        }
        return donante;
    }

}