package Sdonaciones.Importador;

import Sdonaciones.dominio.donante.Donante;
import Sdonaciones.dominio.donante.PersonaHumana;
import Sdonaciones.dominio.donante.PersonaJuridica;
import Sdonaciones.repositorios.RepositorioDonadores;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;


public  class Importador {
    private static Importador instancia = null;
    private RepositorioDonadores repositorioDonadores = null;


    private Importador(){}
    public static Importador GetInstance(){
        if(instancia == null)
            instancia = new Importador();
        return instancia;
    }

    public void setRepositorioDonadores(RepositorioDonadores repo){
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
        Optional<Donante> donanteExistente = repositorioDonadores.getDonadores().stream().filter(donante -> donante.getCorreoElectronico().equals(fila[4])).findFirst();
        if (donanteExistente.isPresent()) {
            int i = repositorioDonadores.getDonadores().indexOf(donanteExistente.get());
            repositorioDonadores.getDonadores().set(i, setearDonante(fila));
        } else {
            repositorioDonadores.getDonadores().add(setearDonante(fila));
        }
    }


    public Donante setearDonante(String[] fila) {
        if (fila[0].equalsIgnoreCase("HUMANA")) {
            return new PersonaHumana(fila[1], fila[2], fila[3], fila[4], fila[5]);
        } else {
            return new PersonaJuridica(fila[1], fila[2], fila[3], fila[4], fila[5]);
        }
    }

}