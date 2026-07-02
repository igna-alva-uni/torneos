package cl.duoc.torneos.controller;

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

import cl.duoc.torneos.dto.TorneosRequest;
import cl.duoc.torneos.dto.TorneosResponse;
import cl.duoc.torneos.service.TorneosService;

@WebMvcTest(TorneosController.class)
@AutoConfigureMockMvc(addFilters = false)
class TorneosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TorneosService torneosService;

    @MockBean
    private cl.duoc.commons.security.JwtTokenProvider jwtTokenProvider;

    @MockBean
    private cl.duoc.commons.security.JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    @DisplayName("GET /api/v1/torneos - Debería retornar todos los torneos")
    void shouldReturnAllTorneos() throws Exception {
        TorneosResponse r = new TorneosResponse();
        r.setId(1);
        r.setNombre("Summer Cup");
        r.setIdJuego(2);

        when(torneosService.findAll()).thenReturn(Collections.singletonList(r));

        mockMvc.perform(get("/api/v1/torneos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Summer Cup"));
    }

    @Test
    @DisplayName("GET /api/v1/torneos/{id} - Debería retornar un torneo")
    void shouldReturnTorneoById() throws Exception {
        TorneosResponse r = new TorneosResponse();
        r.setId(1);
        r.setNombre("Summer Cup");
        r.setIdJuego(2);

        when(torneosService.findById(1)).thenReturn(r);

        mockMvc.perform(get("/api/v1/torneos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Summer Cup"));
    }

    @Test
    @DisplayName("POST /api/v1/torneos - Debería crear un torneo")
    void shouldCreateTorneo() throws Exception {
        TorneosRequest request = new TorneosRequest(2, 1);
        request.setNombre("Summer Cup");

        TorneosResponse r = new TorneosResponse();
        r.setId(1);
        r.setNombre("Summer Cup");
        r.setIdJuego(2);

        when(torneosService.create(any(TorneosRequest.class))).thenReturn(r);

        mockMvc.perform(post("/api/v1/torneos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Summer Cup"));
    }
}
