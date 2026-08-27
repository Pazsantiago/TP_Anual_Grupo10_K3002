package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Repository;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.*;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.DonacionImportada;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.PerfilDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.CategoriasDonante.CategoriaDonante;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.ProgresoMision;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Perfil.RankingPerfil;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositorioPerfiles {

    private final List<PerfilDonante> perfilDonantes = new ArrayList<>();

    public void guardar(PerfilDonante perfil) {
        if (perfil == null) {
            return;
        }
        perfilDonantes.removeIf(existing -> existing.getID() == perfil.getID());
        perfilDonantes.add(perfil);
    }

    public List<PerfilDonante> getDonaciones() {
        return perfilDonantes;
    }

    public PerfilDonante getxId(long donanteId) {
        return perfilDonantes.stream()
                .filter(p -> p.getID() == donanteId)
                .findFirst()
                .orElse(null);
    }

    
      public RepositorioPerfiles() {
          /*esto lo mio
          CategoriaDonante categoriaInicial = new CategoriaDonante();
          categoriaInicial.initCategoria();

          // Instanciamos la misión de Racha
          MisionRacha misionInicial = new MisionRacha();

          // Poblamos sus datos descriptivos
          misionInicial.setId(1L);
          misionInicial.setNombre("Racha Donante");
          misionInicial.setDescripcion("Realizar donaciones consecutivas durante 10 meses");

          RankingPerfil rankingInicial = new RankingPerfil();

          // Asignamos la misión al donante de prueba (ID: 1)
          PerfilDonante donanteDePrueba = new PerfilDonante(
                  1L,
                  categoriaInicial,
                  misionInicial,
                  rankingInicial
          );

          this.perfilDonantes.add(donanteDePrueba);*/
        /* Esto le que armo elian
        CategoriaDonante categoriaInicial = new CategoriaDonante(); 
        categoriaInicial.initCategoria(); 

        MisionCompletitud misionInicial = new MisionCompletitud();
        RankingPerfil rankingInicial = new RankingPerfil();

     
        PerfilDonante donanteDePrueba = new PerfilDonante(
            1L, 
            categoriaInicial, 
            misionInicial, 
            rankingInicial
        );

   
        this.perfilDonantes.add(donanteDePrueba);*/
          // =========================================================================
          // CASO 1: DONANTE INICIAL (ID: 1) - Racha activa desde cero
          // =========================================================================
          CategoriaDonante catInicial1 = new CategoriaDonante();
          catInicial1.initCategoria();

          MisionRacha misionRacha = new MisionRacha();
          misionRacha.setId(1L);
          misionRacha.setNombre("Racha Donante");
          misionRacha.setDescripcion("Realizar donaciones consecutivas durante 10 meses");

          // NUEVO: Insignia para la Misión Racha
          Insignia insRacha = new Insignia();
          insRacha.setID(101L);
          insRacha.setNombre("Llama Solidaria");
          insRacha.setDescripcion("Otorgada por mantener una racha de donaciones");
          misionRacha.setInsignia(insRacha); // Vinculación

          RankingPerfil ranking1 = new RankingPerfil();

          PerfilDonante donante1 = new PerfilDonante(1L, catInicial1, misionRacha, ranking1);
          this.perfilDonantes.add(donante1);


          // =========================================================================
          // CASO 2: DONANTE EN PROGRESO PARCIAL (ID: 2) - Donaciones Exitosas
          // =========================================================================
          CategoriaDonante catInicial2 = new CategoriaDonante();
          catInicial2.initCategoria();

          MisionDonacionesExitosas misionCantidad = new MisionDonacionesExitosas();
          misionCantidad.setId(2L);
          misionCantidad.setNombre("Donador Frecuente");
          misionCantidad.setDescripcion("Realizar 5 donaciones exitosas");
          misionCantidad.setDonacionesExitosasRequeridas(5.0);

          // NUEVO: Insignia para la Misión Donaciones Exitosas
          Insignia insConstancia = new Insignia();
          insConstancia.setID(102L);
          insConstancia.setNombre("Constancia de Hierro");
          insConstancia.setDescripcion("Otorgada por lograr 5 donaciones exitosas");
          misionCantidad.setInsignia(insConstancia); // Vinculación

          RankingPerfil ranking2 = new RankingPerfil();
          PerfilDonante donante2 = new PerfilDonante(2L, catInicial2, misionCantidad, ranking2);

          DonacionImportada d1 = new DonacionImportada();
          d1.setDonacionId(201L); d1.setDonanteId(2L); d1.setCantidadDonada(5);
          d1.setCategoria("ALIMENTOS"); d1.setExitosa(true); d1.setFechaDonacion(LocalDate.of(2026, 1, 10));

          DonacionImportada d2 = new DonacionImportada();
          d2.setDonacionId(202L); d2.setDonanteId(2L); d2.setCantidadDonada(3);
          d2.setCategoria("ROPA"); d2.setExitosa(true); d2.setFechaDonacion(LocalDate.of(2026, 2, 15));

          donante2.getProgreso().actualizar(d1);
          donante2.getProgreso().actualizar(d2);
          donante2.sumarDonacion();
          donante2.sumarDonacion();

          this.perfilDonantes.add(donante2);


          // =========================================================================
          // CASO 3: DONANTE EXPERTO (ID: 3) - Misión Completada y Subida de Categoría
          // =========================================================================
          CategoriaDonante catInicial3 = new CategoriaDonante();
          catInicial3.initCategoria();

          MisionCompletitud misionVariedad = new MisionCompletitud();
          misionVariedad.setId(3L);
          misionVariedad.setNombre("Donador Variado");
          misionVariedad.setDescripcion("Donar en 3 categorías distintas");
          misionVariedad.setCategoriasDistintasRequeridas(3.0);

          // Insignia ya implementada correctamente en tu código original
          Insignia insVariedad = new Insignia();
          insVariedad.setID(103L);
          insVariedad.setNombre("Donador Versátil");
          insVariedad.setDescripcion("Otorgada por donar en 3 categorías distintas");
          misionVariedad.setInsignia(insVariedad); // Vinculación directa

          RankingPerfil ranking3 = new RankingPerfil();
          PerfilDonante donante3 = new PerfilDonante(3L, catInicial3, misionVariedad, ranking3);

          DonacionImportada d31 = new DonacionImportada();
          d31.setDonacionId(301L); d31.setDonanteId(3L); d31.setCantidadDonada(2);
          d31.setCategoria("ALIMENTOS"); d31.setExitosa(true); d31.setFechaDonacion(LocalDate.of(2026, 3, 1));

          DonacionImportada d32 = new DonacionImportada();
          d32.setDonacionId(302L); d32.setDonanteId(3L); d32.setCantidadDonada(1);
          d32.setCategoria("ROPA"); d32.setExitosa(true); d32.setFechaDonacion(LocalDate.of(2026, 3, 2));

          DonacionImportada d33 = new DonacionImportada();
          d33.setDonacionId(303L); d33.setDonanteId(3L); d33.setCantidadDonada(4);
          d33.setCategoria("CALZADO"); d33.setExitosa(true); d33.setFechaDonacion(LocalDate.of(2026, 3, 3));

          donante3.getProgreso().actualizar(d31);
          donante3.getProgreso().actualizar(d32);
          donante3.getProgreso().actualizar(d33);
          donante3.sumarDonacion();
          donante3.sumarDonacion();
          donante3.sumarDonacion();

          donante3.misionCompletada();
          donante3.subirCategoria();

          this.perfilDonantes.add(donante3);

          // =========================================================================
          // CASO 4: DONANTE VETERANO (ID: 4) - Múltiples Insignias Cargadas
          // =========================================================================
          CategoriaDonante catInicial4 = new CategoriaDonante();
          catInicial4.initCategoria();

          MisionHabilDonador misionMonto = new MisionHabilDonador();
          misionMonto.setId(4L);
          misionMonto.setNombre("Gran Aportante");
          misionMonto.setDescripcion("Aportar un total de 50 elementos");
          misionMonto.setCantidadBienesRequerida(50.0);

          // NUEVO: Insignia para la Misión Habil Donador
          Insignia insGranAportante = new Insignia();
          insGranAportante.setID(104L);
          insGranAportante.setNombre("Corazón de Oro");
          insGranAportante.setDescripcion("Otorgada por aportar 50 bienes en total");
          misionMonto.setInsignia(insGranAportante); // Vinculación

          RankingPerfil ranking4 = new RankingPerfil();
          PerfilDonante donante4 = new PerfilDonante(4L, catInicial4, misionMonto, ranking4);

          donante4.sumarDonacion();
          donante4.sumarDonacion();
          donante4.sumarDonacion();
          donante4.sumarDonacion();
          donante4.sumarDonacion();

          // Historial de insignias pasadas ya ganadas
          if (donante4.getInsignias() != null) {
              Insignia insPasada1 = new Insignia();
              insPasada1.setID(1001L); // ID cambiado para no chocar con las de misiones actuales
              insPasada1.setNombre("Primera Donación");
              insPasada1.setDescripcion("Otorgada por la primera donación");

              Insignia insPasada2 = new Insignia();
              insPasada2.setID(1002L); // ID cambiado
              insPasada2.setNombre("Donador de Invierno");
              insPasada2.setDescripcion("Otorgada por donar ropa en julio");

              donante4.getInsignias().add(insPasada1);
              donante4.getInsignias().add(insPasada2);
          }

          donante4.subirCategoria();
          donante4.subirCategoria();

          this.perfilDonantes.add(donante4);
    }

}
