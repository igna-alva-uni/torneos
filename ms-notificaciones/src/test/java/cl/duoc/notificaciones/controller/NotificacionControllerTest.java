package cl.duoc.notificaciones.controller;

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

import cl.duoc.notificaciones.dto.TipoNotificacionRequestDTO;
import cl.duoc.notificaciones.dto.TipoNotificacionResponseDTO;
import cl.duoc.notificaciones.service.NotificacionService;

@WebMvcTest(NotificacionController.class)
@AutoConfigureMockMvc(addFilters = false)
class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NotificacionService service;

    @MockBean
    private cl.duoc.commons.security.JwtTokenProvider jwtTokenProvider;

    @MockBean
    private cl.duoc.commons.security.JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    @DisplayName("POST /api/v1/notificaciones/tipos - Debería crear un tipo de notificación")
    void shouldCreateTipo() throws Exception {
        TipoNotificacionRequestDTO request = new TipoNotificacionRequestDTO();
        request.setNombreTipoNotificacion("EMAIL");

        TipoNotificacionResponseDTO response = new TipoNotificacionResponseDTO();
        response.setIdTipoNotificacion(1);
        response.setNombreTipoNotificacion("EMAIL");

        when(service.crearTipo(any(TipoNotificacionRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/notificaciones/tipos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreTipoNotificacion").value("EMAIL"));
    }
}
