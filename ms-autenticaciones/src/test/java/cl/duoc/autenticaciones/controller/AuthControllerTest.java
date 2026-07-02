package cl.duoc.autenticaciones.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

import cl.duoc.autenticaciones.dtos.auth.AuthRequest;
import cl.duoc.autenticaciones.dtos.auth.AuthResponse;
import cl.duoc.autenticaciones.service.AuthService;
import cl.duoc.autenticaciones.service.RoleService;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private cl.duoc.commons.security.JwtTokenProvider jwtTokenProvider;

    @MockBean
    private cl.duoc.commons.security.JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    @DisplayName("POST /api/v1/autenticaciones/usuarios - Debería crear credenciales")
    void shouldRegisterAuth() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setIdUsuario(1L);
        request.setEmail("john@example.com");
        request.setPassword("password123");
        request.setNombresRoles(new HashSet<>());

        AuthResponse r = new AuthResponse();
        r.setIdUsuario(1L);
        r.setEmail("john@example.com");

        when(authService.register(any(AuthRequest.class))).thenReturn(r);

        mockMvc.perform(post("/api/v1/autenticaciones/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUsuario").value(1L));
    }
}
