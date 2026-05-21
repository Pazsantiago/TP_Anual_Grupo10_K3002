package ar.edu.utn.donatrack;

import ar.edu.utn.donatrack.dominio.bien.Bien;
import ar.edu.utn.donatrack.dominio.bien.BienPerecedero;
import ar.edu.utn.donatrack.dominio.bien.Estado;
import ar.edu.utn.donatrack.dominio.categoria.Subcategoria;
import ar.edu.utn.donatrack.dominio.donacion.Donacion;
import ar.edu.utn.donatrack.dominio.donacion.EstadoDonacion;
import ar.edu.utn.donatrack.dominio.donador.PersonaHumana;
import ar.edu.utn.donatrack.dominio.donador.PersonaJuridica;
import ar.edu.utn.donatrack.dominio.donador.TipoEmpresa;
import ar.edu.utn.donatrack.dominio.entidad.EntidadBeneficiaria;
import ar.edu.utn.donatrack.dominio.necesidad.NecesidadExtraordinaria;
import ar.edu.utn.donatrack.dominio.necesidad.NecesidadRecurrente;
import ar.edu.utn.donatrack.notificaciones.servicios.ServicioNotificacionesConsola;
import ar.edu.utn.donatrack.repositorios.*;
import ar.edu.utn.donatrack.servicios.ServicioDonaciones;
import ar.edu.utn.donatrack.servicios.ServicioImportacionCSV;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServicioDonacionesTest {

    private ServicioDonaciones servicio;
    private ServicioNotificacionesConsola notificaciones;
    private RepositorioDonadores repoDonadores;

    // Categorías y subcategorías de prueba
    private ar.edu.utn.donatrack.dominio.categoria.Categoria catAlimentos;
    private ar.edu.utn.donatrack.dominio.categoria.Categoria catMobiliario;
    private Subcategoria subFideos;
    private Subcategoria subTomate;
    private Subcategoria subSillas;

    @BeforeEach
    void setUp() {
        repoDonadores    = new RepositorioDonadores();
        notificaciones   = new ServicioNotificacionesConsola();
        servicio = new ServicioDonaciones(
            repoDonadores,
            new RepositorioDonaciones(),
            new RepositorioEntidades(),
            notificaciones
        );

        catAlimentos  = CategoriaFactory.crear(CategoriaFactory.TipoCategoria.ALIMENTOS);
        catMobiliario = CategoriaFactory.crear(CategoriaFactory.TipoCategoria.MOBILIARIO);
        subFideos  = new SubcategoriaGenerica("Fideos secos", 1, "paquetes", catAlimentos);
        subTomate  = new SubcategoriaGenerica("Tomate triturado", 1, "tetra-packs", catAlimentos);
        subSillas  = new SubcategoriaGenerica("Sillas", 1, "unidades", catMobiliario);
    }

    // ─── Tests de registro de donadores ─────────────────────────────────────

    @Test
    @DisplayName("Registrar persona humana donadora correctamente")
    void registrarPersonaHumana() {
        var persona = new PersonaHumana("Ana", "Pérez", 30,
                "12345678", "F", "Av. Corrientes 1234", "ana@mail.com");

        servicio.registrarDonador(persona);

        var encontrado = servicio.buscarDonadorPorCorreo("ana@mail.com");
        assertEquals("Ana", ((PersonaHumana) encontrado).getNombre());
    }

    @Test
    @DisplayName("No se puede registrar dos donadores con el mismo correo")
    void noDuplicarDonador() {
        var p1 = new PersonaHumana("Ana", "Pérez", 30, "12345678", "F",
                "Av. Corrientes", "ana@mail.com");
        var p2 = new PersonaHumana("Ana", "Otro", 25, "87654321", "F",
                "Otra calle", "ana@mail.com");

        servicio.registrarDonador(p1);
        assertThrows(IllegalStateException.class, () -> servicio.registrarDonador(p2));
    }

    @Test
    @DisplayName("Se envía notificación de bienvenida al registrarse")
    void notificacionBienvenida() {
        var persona = new PersonaHumana("Carlos", "López", 40, "22222222",
                "M", "Av. 9 de Julio", "carlos@mail.com");
        servicio.registrarDonador(persona);

        var notifs = notificaciones.getHistorial();
        assertEquals(1, notifs.size());
        assertEquals("carlos@mail.com", notifs.get(0).getDestinatario());
    }

    // ─── Tests de donaciones y segmentación ──────────────────────────────────

    @Test
    @DisplayName("Escenario: Planta industrial dona fideos y tomate con distinta fecha de vencimiento")
    void donacionPerecederosDiferenteFechaVencimiento() {
        // Planta industrial de pastas: 100 paquetes de fideos + 50 tetra-packs de tomate
        var empresa = new PersonaJuridica("Pastas del Sur S.A.", TipoEmpresa.EMPRESA, "Alimentaria",
                new ar.edu.utn.donatrack.dominio.donador.MedioContacto(ar.edu.utn.donatrack.dominio.donador.MedioContacto.Tipo.CORREO_ELECTRONICO, "pastas@mail.com"));
        servicio.registrarDonador(empresa);

        var fideos = new BienPerecedero("Fideos tipo spaghetti", subFideos,
                100, LocalDate.of(2027, 1, 1));
        var tomate = new BienPerecedero("Tomate triturado tetra-pack", subTomate,
                50, LocalDate.of(2027, 6, 15));  // distinta fecha → segmento separado

        Donacion donacion = servicio.registrarDonacion(
            empresa, "admin-01",
            "Donación de pastas y tomates",
            List.of(fideos, tomate)
        );

        // Dos bienes de distinta subcategoría → dos donaciones segmentadas
        assertEquals(2, donacion.getDonacionesSegmentadas().size());
        donacion.getDonacionesSegmentadas()
                .forEach(ds -> assertEquals(EstadoDonacion.PENDIENTE_ASIGNACION, ds.getEstado()));
    }

    @Test
    @DisplayName("Escenario: Arcos Plateados dona muebles usados (sillas + mesa)")
    void donacionMueblesUsados() {
        var subMesa = new SubcategoriaGenerica("Mesa rectangular", 1, "unidades", catMobiliario);
        var persona = new PersonaHumana("Juan", "García", 50, "33333333",
                "M", "Florida 800", "juan@arcosplateados.com");
        servicio.registrarDonador(persona);

        var sillas = new Bien("Sillas de escritorio", subSillas, 6, Estado.USADO, null);
        var mesa   = new Bien("Mesa rectangular de madera", subMesa, 1, Estado.USADO, null);

        Donacion donacion = servicio.registrarDonacion(
            persona, "admin-02",
            "Muebles de mudanza corporativa",
            List.of(sillas, mesa)
        );

        assertEquals(2, donacion.getDonacionesSegmentadas().size());
        // Verificar que ambas tienen el estado inicial correcto
        assertTrue(donacion.getDonacionesSegmentadas().stream()
                .allMatch(ds -> ds.getEstado() == EstadoDonacion.PENDIENTE_ASIGNACION));
    }

    @Test
    @DisplayName("No se puede registrar una donación sin bienes")
    void donacionSinBienesLanzaExcepcion() {
        var persona = new PersonaHumana("María", "Rodríguez", 28, "44444444",
                "F", "Callao 500", "maria@mail.com");
        servicio.registrarDonador(persona);
        assertThrows(IllegalArgumentException.class,
            () -> servicio.registrarDonacion(persona, "admin-01", "Vacía", List.of()));
    }

    // ─── Tests de entidades beneficiarias y necesidades ──────────────────────

    @Test
    @DisplayName("Necesidad extraordinaria se satisface con donaciones parciales")
    void necesidadExtraordinariaParial() {
        // Escuela rural necesita 30 sillas
        var necesidad = new NecesidadExtraordinaria(subSillas,
                "Reposición tras inundación", 30);

        assertFalse(necesidad.estaSatisfecha());

        necesidad.recibirBienes(2);   // primera donación: 2 sillas
        assertFalse(necesidad.estaSatisfecha());

        necesidad.recibirBienes(20);  // segunda: 20 sillas
        assertFalse(necesidad.estaSatisfecha());

        necesidad.recibirBienes(8);   // tercera: 8 sillas → total 30
        assertTrue(necesidad.estaSatisfecha());
    }

    @Test
    @DisplayName("Necesidad recurrente se reinicia por período")
    void necesidadRecurrentePorPeriodo() {
        // Comedor necesita 100 paquetes de fideos por período
        var necesidad = new NecesidadRecurrente(subFideos,
                "Consumo semanal del comedor", 100, NecesidadRecurrente.Periodo.MENSUAL);

        necesidad.recibirBienes(60);
        assertFalse(necesidad.estaSatisfecha());

        necesidad.recibirBienes(40);
        assertTrue(necesidad.estaSatisfecha());
    }

    @Test
    @DisplayName("Registrar entidad beneficiaria y agregar necesidad")
    void registrarEntidadConNecesidad() {
        var entidad = new EntidadBeneficiaria(
            "Escuela Rural N°10",
            "Ruta 40 km 200, Mendoza",
            "+54 261 555-0000",
            List.of("directora@escuela10.edu.ar")
        );
        servicio.registrarEntidadBeneficiaria(entidad);

        var necesidad = new NecesidadExtraordinaria(subSillas, "Tras inundación", 30);
        servicio.registrarNecesidad("Escuela Rural N°10", necesidad);

        var entidades = servicio.listarEntidades();
        assertEquals(1, entidades.size());
        assertEquals(1, entidades.get(0).getNecesidades().size());
    }

    // ─── Tests de importación CSV ─────────────────────────────────────────────

    @Test
    @DisplayName("Importación CSV crea nuevos donantes y actualiza existentes")
    void importacionCSV() throws Exception {
        // Pre-registrar un donante para probar actualización
        var existente = new PersonaHumana("Ana", "Pérez", 30, "12345678",
                "F", "Corrientes 1234", "ana@mail.com");
        repoDonadores.guardar(existente);

        String csv = """
            TipoPersona,TipoDoc,Documento,Nombre/RazonSocial,Email,Telefono
            HUMANA,DNI,12345678,Ana Actualizada,ana@mail.com,+54 11 5555-5555
            JURIDICA,CUIT,30-12345678-9,Arcos Plateados S.A.,contacto@empresa.com,+54 11 4444-4444
            HUMANA,DNI,99999999,Carlos Nuevo,carlos@nuevo.com,
            """;

        var servCSV = new ServicioImportacionCSV(repoDonadores, notificaciones);
        var resultado = servCSV.importar(
            new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8))
        );

        assertEquals(2, resultado.creados());   // Arcos + Carlos
        assertEquals(1, resultado.actualizados()); // Ana
        assertEquals(0, resultado.errores());
    }

    @Test
    @DisplayName("Importación CSV reporta error en líneas malformadas")
    void importacionCSVConError() throws Exception {
        String csv = """
            TipoPersona,TipoDoc,Documento,Nombre/RazonSocial,Email,Telefono
            MALFORMADO
            HUMANA,DNI,11111111,Pedro García,pedro@mail.com,
            """;

        var servCSV = new ServicioImportacionCSV(repoDonadores, notificaciones);
        var resultado = servCSV.importar(
            new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8))
        );

        assertEquals(1, resultado.errores());
        assertEquals(1, resultado.creados());
    }
}
