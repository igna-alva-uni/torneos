package cl.duoc.inscripciones.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
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

import cl.duoc.inscripciones.dto.InscripcionRequest;
import cl.duoc.inscripciones.model.Inscripcion;
import cl.duoc.inscripciones.service.InscripcionService;

@WebMvcTest(InscripcionController.class)
@AutoConfigureMockMvc(addFilters = false)
class InscripcionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InscripcionService inscripcionService;

    @MockBean
    private cl.duoc.commons.security.JwtTokenProvider jwtTokenProvider;

    @MockBean
    private cl.duoc.commons.security.JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    @DisplayName("POST /api/v1/inscripciones - Debería crear una inscripción")
    void shouldCreateInscripcion() throws Exception {
        InscripcionRequest request = new InscripcionRequest();
        request.setIdUsuario(1L);
        request.setIdTorneo(2L);

        Inscripcion entity = Inscripcion.builder()
                .idInscripcion(10L)
                .idUsuario(1L)
                .idTorneo(2L)
                .estado("PENDIENTE")
                .fechaInscripcion(LocalDateTime.now())
                .build();

        when(inscripcionService.crear(any(InscripcionRequest.class))).thenReturn(entity);

        mockMvc.perform(post("/api/v1/inscripciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idInscripcion").value(10L))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    @Test
    @DisplayName("GET /api/v1/inscripciones - Debería retornar todas")
    void shouldReturnAll() throws Exception {
        Inscripcion entity = Inscripcion.builder()
                .idInscripcion(10L)
                .idUsuario(1L)
                .idTorneo(2L)
                .estado("PENDIENTE")
                .build();

        when(inscripcionService.listar()).thenReturn(Collections.singletonList(entity));

        mockMvc.perform(get("/api/v1/inscripciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idInscripcion").value(10L));
    }
}
