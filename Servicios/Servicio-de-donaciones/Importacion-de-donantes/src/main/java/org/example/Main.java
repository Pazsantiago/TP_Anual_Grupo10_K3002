package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

//public enum TipoPersona{
//    HUMANA, JURIDICA;
//}
//public enum TipoDoc{
//    DNI, CUIT;
//}

enum TipoEmpresa{
    SA, SSR;
}

class Contacto{
    String correo;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getNumeroContacto() {
        return numeroContacto;
    }

    public void setNumeroContacto(int numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    int numeroContacto;
}

class PersonaHumana extends Donante{
    String apellido;
    int edad;
    String genero;

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(List<Contacto> contactos) {
        this.contactos = contactos;
    }

    String direccion;
    List<Contacto> contactos;
    public PersonaHumana(String tipoD, String doc, String nom, String em, String tel) {
        super(tipoD, doc, nom, em, tel);
    }
}
class PersonaJuridica extends Donante{
    TipoEmpresa tipoEmpresa;
    String rubro;

    public TipoEmpresa getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public List<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(List<Contacto> contactos) {
        this.contactos = contactos;
    }

    public List<PersonaHumana> getRepresentantes() {
        return representantes;
    }

    public void setRepresentantes(List<PersonaHumana> representantes) {
        this.representantes = representantes;
    }

    List<Contacto> contactos;
    List<PersonaHumana> representantes;
    public PersonaJuridica(String tipoD, String doc, String nom, String em, String tel) {
        super(tipoD, doc, nom, em, tel);
    }

}

class Donante{
    private String tipoDoc;

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    private String documento;
    private String nombre;

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    private String correoElectronico;
    private String telefono;

    public Donante(String tipoD, String doc, String nom, String em, String tel) {
        tipoDoc = tipoD;
        documento = doc;
        nombre = nom;
        correoElectronico = em;
        telefono = tel;
    }


    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}

class Importador {
    private static Importador instancia = null;

    private Importador(){}

    public static Importador GetInstance(){
        if(instancia == null)
            instancia = new Importador();
        return instancia;
    }

    private final List<Donante> donantesExistentes = new ArrayList<>();
    //El final hace que no se pueda asignar otra referencia a la lista
    //es decir, se puede modificar pero no hacer que donantesExistentes = OtraLista

    public void importarCsv(String ruta_archivo) {
        try (CSVReader csvReader = new CSVReader(new FileReader(ruta_archivo))) {
            String[] fila;
            while ((fila = csvReader.readNext()) != null) {
                controlarDonanteEnLista(fila);
//                System.out.println("El donante es: " + donantes.getLast().getNombre());
            }
            donantesExistentes.removeFirst(); // El primero es el header ;)
//            System.out.println("El donante es: " + donantesExistentes.get(0).getNombre());
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public void controlarDonanteEnLista(String[] fila) {
        Optional<Donante> donanteExistente = donantesExistentes.stream().filter(donante -> donante.getCorreoElectronico().equals(fila[4])).findFirst();
        if (donanteExistente.isPresent()) {
            int i = donantesExistentes.indexOf(donanteExistente.get());
            donantesExistentes.set(i, setearDonante(fila));
        } else {
            donantesExistentes.add(setearDonante(fila));
        }
    }

    public List<Donante> getDonantesExistentes() {
        return donantesExistentes;
    }


    public Donante setearDonante(String[] fila) {
        if (fila[0].equalsIgnoreCase("HUMANA")) {
            return new PersonaHumana(fila[1], fila[2], fila[3], fila[4], fila[5]);
        } else {
            return new PersonaJuridica(fila[1], fila[2], fila[3], fila[4], fila[5]);
        }
    }

}

public class Main {
    public static void main(String[] args) {
        List<Donante> donantes;
        Importador importador = Importador.GetInstance();
        importador.importarCsv("Servicios/Servicio-de-donaciones/Importacion-de-donantes/donantes_import_20000_UTF8_BOM.csv");
        System.out.println("La cantidad de donantes es: " + importador.getDonantesExistentes().size() +
                " Y el ultimo es con el numero: " + importador.getDonantesExistentes().getLast().getTelefono());
    }
}
