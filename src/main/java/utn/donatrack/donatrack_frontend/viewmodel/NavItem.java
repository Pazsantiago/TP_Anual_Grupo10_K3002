package utn.donatrack.donatrack_frontend.viewmodel;

/** Una seccion del menu principal. `clave` es la que cada pagina pasa a header(activo) para marcarla como actual. */
public record NavItem(String clave, String etiqueta, String ruta, String descripcion) {}
