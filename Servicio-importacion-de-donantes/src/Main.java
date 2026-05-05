import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
//public enum TipoPersona{
//    HUMANA, JURIDICA;
//}
//public enum TipoDoc{
//    DNI, CUIT;
//}
 class Donante{
     private String tipoPersona;
     private String tipoDoc;
     private String documento;
     private String nombre;
     private String email;
     private String telefono;

     public Donante(String tipoP, String tipoD, String doc, String nom, String em, String tel) {
         tipoPersona = tipoP;
         tipoDoc = tipoD;
         documento = doc;
         nombre = nom;
         email = em;
         telefono = tel;
     }

     public String getTipoPersona() {
         return tipoPersona;
     }

     public void setTipoPersona(String tipoPersona) {
         this.tipoPersona = tipoPersona;
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

     public String getEmail() {
         return email;
     }

     public void setEmail(String email) {
         this.email = email;
     }

     public String getTelefono() {
         return telefono;
     }

     public void setTelefono(String telefono) {
         this.telefono = telefono;
     }

}

class Importador {

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
        Optional<Donante> donanteExistente = donantesExistentes.stream().filter(donante -> donante.getEmail().equals(fila[4])).findFirst();
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
    public Donante setearDonante(String[] fila){
        return new Donante(fila[0],fila[1],fila[2],fila[3],fila[4], fila[5]);

    }

}

void main() {
    List<Donante> donantes;
    Importador importador = new Importador();
    importador.importarCsv("Servicio-importacion-de-donantes/donantes_import_20000_UTF8_BOM.csv");
//    System.out.println("La cantidad de donantes es: " + importador.getDonantesExistentes().size() +
//            " Y el ultimo es con el numero: " + importador.getDonantesExistentes().getLast().getTelefono());
}
