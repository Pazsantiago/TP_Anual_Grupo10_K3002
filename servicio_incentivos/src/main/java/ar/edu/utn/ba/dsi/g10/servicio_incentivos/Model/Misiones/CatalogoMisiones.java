package ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones;

import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Insignia;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.Mision;
import ar.edu.utn.ba.dsi.g10.servicio_incentivos.Model.Misiones.MisionCompletitud;
import org.springframework.stereotype.Component;

@Component
public class CatalogoMisiones {

  public Mision obtenerPrimeraMision() {
    MisionCompletitud mision = new MisionCompletitud();

    //mision propiesta p inicializar
    mision.setId(1L);
    mision.setNombre("Diversidad solidaria");
    mision.setDescripcion("Realizar donaciones de 2 categorías distintas");
    mision.setCategoriasDistintasRequeridas(2.0);
    mision.setOrden(1);

    Insignia insignia = new Insignia();

    insignia.setID(1L);
    insignia.setNombre("Primeros aportes");
    insignia.setDescripcion(
        "Otorgada por donar bienes de 2 categorías distintas"
    );

    mision.setInsignia(insignia);

    return mision;
  }
}