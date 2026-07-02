package cl.duoc.juegos.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.HashSet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.duoc.juegos.dto.JuegoRequest;
import cl.duoc.juegos.dto.JuegoResponse;
import cl.duoc.juegos.service.JuegoService;

@WebMvcTest(JuegoController.class)
@AutoConfigureMockMvc(addFilters = false)
class JuegoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JuegoService juegoService;

    @MockBean
    private cl.duoc.commons.security.JwtTokenProvider jwtTokenProvider;

    @MockBean
    private cl.duoc.commons.security.JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    @DisplayName("GET /api/v1/juegos - Debería retornar todos los juegos")
    void shouldReturnAllJuegos() throws Exception {
        JuegoResponse r = new JuegoResponse();
        r.setId(1);
        r.setNombre("Minecraft");

        when(juegoService.findAll()).thenReturn(Collections.singletonList(r));

        mockMvc.perform(get("/api/v1/juegos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Minecraft"));
    }

    @Test
    @DisplayName("GET /api/v1/juegos/{id} - Debería retornar un juego")
    void shouldReturnJuegoById() throws Exception {
        JuegoResponse r = new JuegoResponse();
        r.setId(1);
        r.setNombre("Minecraft");

        when(juegoService.findById(1)).thenReturn(r);

        mockMvc.perform(get("/api/v1/juegos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Minecraft"));
    }

    @Test
    @DisplayName("POST /api/v1/juegos - Debería crear un juego")
    void shouldCreateJuego() throws Exception {
        JuegoRequest request = JuegoRequest.builder()
                .nombre("Minecraft")
                .idGenero(1)
                .plataformas(new HashSet<>())
                .descripcion("Sandbox")
                .build();

        JuegoResponse r = new JuegoResponse();
        r.setId(1);
        r.setNombre("Minecraft");

        when(juegoService.create(any(JuegoRequest.class))).thenReturn(r);

        mockMvc.perform(post("/api/v1/juegos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Minecraft"));
    }

    @Test
    @DisplayName("PUT /api/v1/juegos/{id} - Debería actualizar un juego")
    void shouldUpdateJuego() throws Exception {
        JuegoRequest request = JuegoRequest.builder()
                .nombre("Minecraft Editado")
                .idGenero(1)
                .plataformas(new HashSet<>())
                .descripcion("Sandbox")
                .build();

        JuegoResponse r = new JuegoResponse();
        r.setId(1);
        r.setNombre("Minecraft Editado");

        when(juegoService.update(eq(1), any(JuegoRequest.class))).thenReturn(r);

        mockMvc.perform(put("/api/v1/juegos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Minecraft Editado"));
    }

    @Test
    @DisplayName("DELETE /api/v1/juegos/{id} - Debería eliminar el juego")
    void shouldDeleteJuego() throws Exception {
        mockMvc.perform(delete("/api/v1/juegos/1"))
                .andExpect(status().isNoContent());
    }
}
