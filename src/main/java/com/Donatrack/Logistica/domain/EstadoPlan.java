package com.Donatrack.Logistica.domain;

public enum EstadoPlan {
    PLANIFICADA,   // la ruta ya llegó del proveedor, el chofer no salió
    EN_CURSO,      // el chofer indicó que inició el recorrido
    FINALIZADA
  
}
