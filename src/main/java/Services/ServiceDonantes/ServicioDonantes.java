package Services.ServiceDonantes;

import Sdonaciones.Importador.Importador;
import Sdonaciones.dominio.donante.Donante;
import Sdonaciones.repositorios.RepoDonantes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioDonantes {
    private final Importador importadorCSV = Importador.GetInstance();
    private final RepoDonantes repoDonantes;

    public ServicioDonantes(RepoDonantes repoDonantes) {
        this.repoDonantes = repoDonantes;
    }


    public List<Donante> getAllPersonas() {
        return repoDonantes.listarTodos();
    }


    public Donante obtenerPersonaPorDocumento(String tipoD, String doc) {
        return repoDonantes.buscarPorDocumento(tipoD, doc);
    }

    public Donante createPersona(Donante persona) {
        repoDonantes.guardar(persona);
        return persona;

    }

    public String importarCSV(String rutaArchivo) {
        importadorCSV.setRepositorioDonadores(repoDonantes);
        Integer cantDonantesAnteriores = repoDonantes.cantidadDeDonantes();
        boolean importado = importadorCSV.importarCsv(rutaArchivo);

        if (importado) {
            return "Archivo importado correctamente" +
                    " - Cantidad de registros creados: " + (repoDonantes.cantidadDeDonantes() - cantDonantesAnteriores);
        }

        return "No se pudo importar el archivo";
    }


    public Donante updateDonante(String tipoD, String doc, Donante updateDonante) {
        repoDonantes.actualizarDonante(tipoD, doc, updateDonante);
        return updateDonante;
    }


    public String deletePersona(String tipoD, String doc) {
        repoDonantes.eliminarDonante(tipoD, doc);
        return "Donante borrado";
    }
}
