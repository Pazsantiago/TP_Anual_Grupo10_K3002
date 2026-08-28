package com.Donatrack.Logistica.DonaTrackTest.controller;

@SpringBootTest
@AutoConfigureMockMvc
class DonacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DonacionRepository donacionRepository;

    @Test
    void testObtenerPendientes() throws Exception {
        Donacion d1 = new Donacion(1, List.of(), new Direccion("Av. Corrientes 1234"), "Juan", EstadoEntrega.PENDIENTE);
        Donacion d2 = new Donacion(2, List.of(), new Direccion("Av. Santa Fe 2500"), "Maria", EstadoEntrega.PENDIENTE);

        when(donacionRepository.findByEstadoDonacion(EstadoEntrega.PENDIENTE))
                .thenReturn(List.of(d1, d2));

        mockMvc.perform(get("/api/donaciones/pendientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].destinatario").value("Juan"))
                .andExpect(jsonPath("$[1].destinatario").value("Maria"));
    }
}
