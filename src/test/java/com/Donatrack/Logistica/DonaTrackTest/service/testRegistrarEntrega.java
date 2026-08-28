@Test
void testRegistrarEntrega() {
    Entrega entrega = new Entrega();
    entrega.setEstado("Pendiente");

    servicioLogistica.registrarEntrega(entrega);

    assertEquals("Entregada", entrega.getEstado());
    verify(notificacionService, times(1)).notificarEntrega(entrega);
}
