package cl.duoc.estadisticas.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.duoc.estadisticas.dto.equipo.EstadisticaEquipoRequest;
import cl.duoc.estadisticas.dto.equipo.EstadisticaEquipoResponse;
import cl.duoc.estadisticas.service.EstadisticaEquipoService;
import cl.duoc.estadisticas.service.EstadisticaJugadorService;
import cl.duoc.estadisticas.service.EstadisticaPartidaService;

@WebMvcTest(EstadisticaController.class)
@AutoConfigureMockMvc(addFilters = false)
class EstadisticaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EstadisticaEquipoService equipoService;

    @MockBean
    private EstadisticaJugadorService jugadorService;

    @MockBean
    private EstadisticaPartidaService partidaService;

    @MockBean
    private cl.duoc.commons.security.JwtTokenProvider jwtTokenProvider;

    @MockBean
    private cl.duoc.commons.security.JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    @DisplayName("GET /api/v1/stats/equipos - Debería retornar todas las estadísticas de equipos")
    void shouldReturnAllTeamStats() throws Exception {
        EstadisticaEquipoResponse r = new EstadisticaEquipoResponse();
        r.setIdEstadisticaEquipo(1);
        r.setIdEquipo(5);

        when(equipoService.findAll()).thenReturn(Collections.singletonList(r));

        mockMvc.perform(get("/api/v1/stats/equipos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEstadisticaEquipo").value(1));
    }

    @Test
    @DisplayName("POST /api/v1/stats/equipos - Debería crear estadísticas de equipo")
    void shouldCreateTeamStats() throws Exception {
        EstadisticaEquipoRequest request = EstadisticaEquipoRequest.builder()
                .idEquipo(5)
                .victoriasEquipo(10)
                .derrotasEquipo(2)
                .build();

        EstadisticaEquipoResponse r = new EstadisticaEquipoResponse();
        r.setIdEstadisticaEquipo(1);
        r.setIdEquipo(5);

        when(equipoService.create(any(EstadisticaEquipoRequest.class))).thenReturn(r);

        mockMvc.perform(post("/api/v1/stats/equipos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idEstadisticaEquipo").value(1));
    }
}
