@Test
void testPlanificarRuta() {
    when(gpsTool.planificarRuta(anyList())).thenReturn(ruta);

    Ruta resultado = servicioLogistica.planificarRuta(List.of("Av. Corrientes 1234", "Av. Santa Fe 2500"));

    assertNotNull(resultado);
    assertEquals(2, resultado.getDestinos().size());
    verify(notificacionService, times(1)).notificarInicioRuta(resultado);
}
