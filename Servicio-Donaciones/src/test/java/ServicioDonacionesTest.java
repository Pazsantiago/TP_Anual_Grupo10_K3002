
import Sdonaciones.Importador.Importador;
import Sdonaciones.dominio.bien.Bien;
import Sdonaciones.dominio.bien.BienPerecedero;
import Sdonaciones.dominio.categoria.Subcategoria;
import Sdonaciones.dominio.categoria.SubcategoriaFactory;
import Sdonaciones.dominio.donacion.Donacion;
import Sdonaciones.dominio.donante.Donante;
import Sdonaciones.dominio.donante.PersonaHumana;
import Sdonaciones.dominio.entidad.EntidadBeneficiaria;
import Sdonaciones.repositorios.*;
import Snotificaciones.Notificacion.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServicioDonacionesTest {

    private ServicioNotificacionesConsola notificaciones;
    private RepositorioDonadores repositorioDonantes;
    private RepositorioEntidades repositorioEntidades;
    private RepositorioDonaciones repositorioDonaciones;

    private Importador importador = Importador.GetInstance();

    private Subcategoria subFideos;
    private Subcategoria subArroz;

    @BeforeEach
    void setUp() {
        repositorioDonantes = new RepositorioDonadores();
        repositorioEntidades    = new RepositorioEntidades();
        repositorioDonaciones    = new RepositorioDonaciones();
        notificaciones   = new ServicioNotificacionesConsola();
        subFideos  = SubcategoriaFactory.obtener("Fideos Secos");
        subArroz =SubcategoriaFactory.obtener("Arroz");
    }

    // ─── Tests de registros ─────────────────────────────────────
    void registrarDonador(Donante donador){
        repositorioDonantes.guardar(donador);
        notificaciones.notificarBienvenida(
                donador.getCorreoElectronico(), donador.getCorreoElectronico());

    }
    @Test
    @DisplayName("Registrar persona humana donadora correctamente")
    void registrarPersonaHumana() {
        var persona = new PersonaHumana("DNI", "16579", "Ana",
                "ana@mail.com","12345678" );
        registrarDonador(persona);

        Donante encontrado = repositorioDonantes.buscarPorCorreo("ana@mail.com").orElseGet(null);
        assertEquals("Ana", (encontrado).getNombre());
    }

    @Test
    @DisplayName("No se puede registrar dos donadores con el mismo correo")
    void noDuplicarDonador() {
        var persona = new PersonaHumana("DNI", "16579", "Ana",
                "ana@mail.com","12345678" );
        var p2 = new PersonaHumana("DNI", "16579", "Ana",
                "ana@mail.com","12345678" );

        registrarDonador(persona);

        assertThrows(IllegalStateException.class, () -> registrarDonador(p2));
    }


    void registrarEntidad(EntidadBeneficiaria ente){
        repositorioEntidades.guardar(ente);
        notificaciones.notificarBienvenida(
                ente.getCorreoRepresentante(), ente.getRazonSocial());

    }

    @Test
    @DisplayName("Registrar entidad correctamente")
    void registrarEntidadBeneficiaria() {
        var entidad = new EntidadBeneficiaria("RaulSA", "16579","ana@mail.com","12345678" );
        registrarEntidad(entidad);

        EntidadBeneficiaria encontrado = repositorioEntidades.buscarPorRazonSocial("RaulSA").orElseGet(null);
        assertEquals("RaulSA", (encontrado).getRazonSocial());
    }

    @Test
    @DisplayName("No se puede registrar dos donadores con el mismo correo")
    void noDuplicarEntidadBeneficiaria() {
        var entidad = new EntidadBeneficiaria("RaulSA", "16579","ana@mail.com","12345678" );
        registrarEntidad(entidad);
        var p2 =new EntidadBeneficiaria("RaulSA", "16579","ana@mail.com","12345678" );

        assertThrows(IllegalStateException.class, () -> registrarEntidad(p2));


    }

    void registrarDonacion(Donacion donacion,Donante ente){
        repositorioDonaciones.guardar(donacion);
        notificaciones.notificarDonacionAsignada(
                ente.getCorreoElectronico(), donacion.getBienes().toString());

    }

    @Test
    @DisplayName("Escenario: Persona dona fideos y arroz con distinta fecha de vencimiento")
    void donacionDeBienes() {

        var persona = new PersonaHumana("DNI", "16579", "Ana",
                "ana@mail.com","12345678" );

        BienPerecedero fideos = new BienPerecedero(
                "Fideos",
                subFideos,4,
                LocalDate.of(2026, 6, 10)
        );

        Bien arroz = new Bien(
                "Tomate", subArroz,5
        );

        Donacion donacion = new Donacion(persona,"juan:231","donacion 1",List.of(fideos,arroz));

        registrarDonacion(donacion,persona);

        assertEquals(2, donacion.getBienes().size());
    }

    @Test
    @DisplayName("No se puede registrar una donación sin bienes")
    void donacionSinBienesLanzaExcepcion() {
        var persona = new PersonaHumana("DNI", "16579", "Ana",
                "ana@mail.com","12345678" );

        assertThrows(IllegalArgumentException.class, ()->new Donacion(persona,"juan:231","donacion 1",List.of()));

    }


    // ─── Tests de importación CSV ─────────────────────────────────────────────

    @Test
    @DisplayName("Importación CSV crea nuevos donantes")
    void importacionCSV() throws Exception {
        importador.setRepositorioDonadores(repositorioDonantes);
        importador.importarCsv("C:\\Users\\Gonzalo Diez\\Desktop\\EstudiosUTN\\VsCode\\ejercitacionParciales\\TP_Anual_Grupo10_K3002\\Servicio-Donaciones\\src\\test\\donantes_import_20000_UTF8_BOM.csv");

        Donante encontrado = repositorioDonantes.buscarPorCorreo("francoruiz6791@servicios.com.ar").orElseGet(null);
        assertEquals("Franco Ruiz", (encontrado).getNombre());
    }

    @Test
    @DisplayName("Importación CSV reporta error en líneas malformadas")
    void importacionCSVConActualizacion()  {
        var persona = new PersonaHumana("DNI", "16579", "Ana",
                "francoruiz6791@servicios.com.ar","12345678" );
        repositorioDonantes.guardar(persona);
        importador.setRepositorioDonadores(repositorioDonantes);
        importador.importarCsv("C:\\Users\\Gonzalo Diez\\Desktop\\EstudiosUTN\\VsCode\\ejercitacionParciales\\TP_Anual_Grupo10_K3002\\Servicio-Donaciones\\src\\test\\donantes_import_20000_UTF8_BOM.csv");

        Donante encontrado = repositorioDonantes.buscarPorCorreo("francoruiz6791@servicios.com.ar").orElseGet(null);
        assertEquals("Franco Ruiz", (encontrado).getNombre());
        assertNotEquals("16579", (encontrado).getDocumento());
        assertNotEquals("Ana", (encontrado).getNombre());
    }
}
