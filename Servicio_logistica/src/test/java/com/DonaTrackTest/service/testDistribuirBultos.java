@Test
void testDistribuirBultos() {
    Map<Camion, List<Bulto>> asignaciones = Map.of(camion, List.of(bulto));
    when(distribuidorDeCargasService.asignarBultos(anyList(), anyList())).thenReturn(asignaciones);

    servicioLogistica.distribuirBultos(List.of(camion), List.of(bulto));

    verify(distribuidorDeCargasService, times(1)).asignarBultos(anyList(), anyList());
}

