# Servicio de Incentivos

## Exposición de operaciones como API

El servicio de incentivos expone operaciones de negocio pensadas para ser consumidas como endpoints, de forma similar a una API REST para probar en Postman.

### Endpoints sugeridos

- POST /incentivos/donaciones
  - Procesa una nueva donación de un donante.
  - Actualiza el progreso de la misión asociada.

- POST /incentivos/donaciones-entregadas
  - Procesa una donación ya entregada.
  - Reutiliza la lógica de actualización de progreso.

- GET /incentivos/{donanteId}/mision
  - Devuelve la misión actual del donante.

- GET /incentivos/{donanteId}/insignias
  - Devuelve las insignias otorgadas al donante.

- GET /incentivos/{donanteId}/metricas
  - Devuelve el estado y métricas del perfil del donante.

### Componentes principales

- Controller: expone las operaciones del servicio como acciones de entrada/salida.
- Service: implementa la lógica de negocio.
- Domain: contiene el perfil del donante, las misiones y el progreso.
