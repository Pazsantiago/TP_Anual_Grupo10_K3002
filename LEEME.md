# Bases para los demás servicios (Donaciones, Incentivos, Logística)

Copiar el contenido de `src/` encima del proyecto del frontend.
Reemplaza `NotificacionesClient`, `RestClientConfig`, `layout.html`, `layout.css`, `base.css` y `table.css`; el resto es nuevo.

## Qué agrega
| Sección | Ruta | Servicio | Qué muestra |
|---|---|---|---|
| Inicio | `/` | – | Tarjetas hacia cada sección |
| Donantes | `/donantes`, `/donantes/{id}/donaciones` | Donaciones | Listado y donaciones de cada donante |
| Entidades | `/entidades`, `/entidades/{id}/necesidades` | Donaciones | Entidades beneficiarias y necesidades |
| Asignaciones | `/asignaciones` (+ botón "Ejecutar algoritmos") | Donaciones | Rankings de los algoritmos de asignación |
| Incentivos | `/incentivos`, `/incentivos/donantes/{id}` | Incentivos | Ranking mensual, métricas, misión, insignias, crear perfil |
| Logística | `/logistica` | Logística | Camiones, bultos y donaciones pendientes |

## Dónde corre cada servicio
El frontend usa por defecto estas URLs (cambiables con variables de entorno o `application.properties`):

| Servicio | Variable | Por defecto |
|---|---|---|
| Donaciones | `DONACIONES_URL` | `http://localhost:8090` |
| Incentivos | `INCENTIVOS_URL` | `http://localhost:8091` |
| Logística | `LOGISTICA_URL` | `http://localhost:8092` |

Los tres servicios traen puertos que chocan entre sí y con el frontend (8080). Para correrlos juntos:
- Donaciones: `SERVER_PORT=8090` y `SERVICIO_INCENTIVOS_URL=http://localhost:8091/incentivos`
- Incentivos: `--server.port=8091`
- Logística: `--server.port=8092` (su `application.properties` fija 8081)

`application.properties` (opcional, solo si querés dejarlo explícito):
```
donatrack.services.donaciones.base-url=${DONACIONES_URL:http://localhost:8090}
donatrack.services.incentivos.base-url=${INCENTIVOS_URL:http://localhost:8091}
donatrack.services.logistica.base-url=${LOGISTICA_URL:http://localhost:8092}
```

## Cómo extender
- **Nueva sección:** una línea en `NavegacionAdvice` (aparece en la barra y en el inicio) + controller + vista.
- **Nuevo endpoint:** un método en el cliente del servicio (usa `call(...)` / `listaDe(...)`).
- **Nuevo servicio:** `Endpoint` en `ServiciosProperties`, `@Bean` en `RestClientConfig`, y un cliente que extienda `ClienteServicio`.
- **Página que carga datos:** `Seccion.cargar(client::metodo)`; si el servicio falla, la página igual se muestra.
