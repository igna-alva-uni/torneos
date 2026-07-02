package cl.duoc.usuarios.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.duoc.usuarios.dtos.user.UserRequest;
import cl.duoc.usuarios.dtos.user.UserResponse;
import cl.duoc.usuarios.service.PaisService;
import cl.duoc.usuarios.service.PerfilService;
import cl.duoc.usuarios.service.UserService;
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private PaisService paisService;

    @MockBean
    private PerfilService perfilService;

    @MockBean
    private cl.duoc.commons.security.JwtTokenProvider jwtTokenProvider;

    @MockBean
    private cl.duoc.commons.security.JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    @DisplayName("POST /api/v1/usuarios/usuarios - Debería registrar un usuario y retornar 201")
    void shouldCreateUserAndReturnCreated() throws Exception {
        UserRequest request = new UserRequest("john_doe", "john@example.com");
        UserResponse response = new UserResponse();
        response.setId(1L);
        response.setUsername("john_doe");
        response.setEmail("john@example.com");

        when(userService.addUser(any(UserRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/usuarios/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    @DisplayName("POST /api/v1/usuarios/usuarios - Debería retornar 400 Bad Request si los datos son inválidos")
    void shouldReturnBadRequestWhenDataInvalid() throws Exception {
        UserRequest request = new UserRequest("sh", "invalid-email"); // username min 5, invalid email

        mockMvc.perform(post("/api/v1/usuarios/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/usuarios/usuarios/{id} - Debería retornar el usuario")
    void shouldGetUserById() throws Exception {
        Long id = 1L;
        UserResponse response = new UserResponse();
        response.setId(id);
        response.setUsername("john_doe");
        response.setEmail("john@example.com");

        when(userService.getUserById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/usuarios/usuarios/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john_doe"));
    }

    @Test
    @DisplayName("PUT /api/v1/usuarios/usuarios/{id} - Debería actualizar el usuario")
    void shouldUpdateUser() throws Exception {
        Long id = 1L;
        UserRequest request = new UserRequest("john_doe_new", "john_new@example.com");
        UserResponse response = new UserResponse();
        response.setId(id);
        response.setUsername("john_doe_new");
        response.setEmail("john_new@example.com");

        when(userService.updateUser(eq(id), any(UserRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/usuarios/usuarios/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john_doe_new"));
    }

    @Test
    @DisplayName("DELETE /api/v1/usuarios/usuarios/{id} - Debería eliminar y retornar 204")
    void shouldDeleteUser() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/v1/usuarios/usuarios/{id}", id))
                .andExpect(status().isNoContent());
    }
}
