package cl.duoc.partidas.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

import cl.duoc.partidas.dto.PartidaRequest;
import cl.duoc.partidas.dto.PartidaResponse;
import cl.duoc.partidas.model.EstadoPartida;
import cl.duoc.partidas.service.PartidaService;

@WebMvcTest(PartidaController.class)
@AutoConfigureMockMvc(addFilters = false)
class PartidaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PartidaService partidaService;

    @MockBean
    private cl.duoc.commons.security.JwtTokenProvider jwtTokenProvider;

    @MockBean
    private cl.duoc.commons.security.JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    @DisplayName("GET /api/v1/partidas - Debería retornar todas las partidas")
    void shouldReturnAllPartidas() throws Exception {
        PartidaResponse r = new PartidaResponse();
        r.setId(1);
        r.setTorneoId(10);
        r.setRonda("Final");

        when(partidaService.findAll()).thenReturn(Collections.singletonList(r));

        mockMvc.perform(get("/api/v1/partidas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ronda").value("Final"));
    }

    @Test
    @DisplayName("POST /api/v1/partidas - Debería crear una partida")
    void shouldCreatePartida() throws Exception {
        PartidaRequest request = PartidaRequest.builder()
                .torneoId(10)
                .ronda("Final")
                .estado(EstadoPartida.PENDIENTE)
                .build();

        PartidaResponse r = new PartidaResponse();
        r.setId(1);
        r.setTorneoId(10);
        r.setRonda("Final");

        when(partidaService.create(any(PartidaRequest.class))).thenReturn(r);

        mockMvc.perform(post("/api/v1/partidas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ronda").value("Final"));
    }
}
