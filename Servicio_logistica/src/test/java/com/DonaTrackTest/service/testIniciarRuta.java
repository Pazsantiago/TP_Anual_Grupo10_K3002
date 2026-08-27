@Test
void testIniciarRuta() {
    ruta.setEstado("Pendiente");

    servicioLogistica.iniciarRuta(ruta);

    assertEquals("En tránsito", ruta.getEstado());
    verify(notificacionService, times(1)).notificarChofer(ruta);
}
