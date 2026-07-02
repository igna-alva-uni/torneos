package cl.duoc.equipos.controller;

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

import cl.duoc.equipos.dtos.equipo.EquipoRequest;
import cl.duoc.equipos.dtos.equipo.EquipoResponse;
import cl.duoc.equipos.service.EquipoService;
import cl.duoc.equipos.service.MiembroEquipoService;
import cl.duoc.equipos.service.RolEquipoService;

@WebMvcTest(EquipoController.class)
@AutoConfigureMockMvc(addFilters = false)
class EquipoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EquipoService equipoService;

    @MockBean
    private RolEquipoService rolService;

    @MockBean
    private MiembroEquipoService miembroService;

    @MockBean
    private cl.duoc.commons.security.JwtTokenProvider jwtTokenProvider;

    @MockBean
    private cl.duoc.commons.security.JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    @DisplayName("POST /api/v1/equipos/equipos - Debería crear un equipo y retornar 201 Created")
    void shouldCreateEquipoAndReturnCreated() throws Exception {
        EquipoRequest request = new EquipoRequest();
        request.setNombreEquipo("SK Telecom T1");

        EquipoResponse response = new EquipoResponse();
        response.setId(1L);
        response.setNombreEquipo("SK Telecom T1");

        when(equipoService.addEquipo(any(EquipoRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/equipos/equipos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombreEquipo").value("SK Telecom T1"))
                .andExpect(jsonPath("$._links.get.href").exists());
    }

    @Test
    @DisplayName("POST /api/v1/equipos/equipos - Debería retornar 400 Bad Request cuando el nombre está vacío")
    void shouldReturnBadRequestWhenNameIsInvalid() throws Exception {
        EquipoRequest request = new EquipoRequest();
        request.setNombreEquipo("");

        mockMvc.perform(post("/api/v1/equipos/equipos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/equipos/equipos/{id} - Debería obtener un equipo por su ID")
    void shouldGetEquipoById() throws Exception {
        Long id = 1L;
        EquipoResponse response = new EquipoResponse();
        response.setId(id);
        response.setNombreEquipo("G2 Esports");

        when(equipoService.getById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/equipos/equipos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombreEquipo").value("G2 Esports"));
    }

    @Test
    @DisplayName("PUT /api/v1/equipos/equipos/{id} - Debería actualizar un equipo")
    void shouldUpdateEquipo() throws Exception {
        Long id = 1L;
        EquipoRequest request = new EquipoRequest();
        request.setNombreEquipo("G2 Esports Renovado");

        EquipoResponse response = new EquipoResponse();
        response.setId(id);
        response.setNombreEquipo("G2 Esports Renovado");

        when(equipoService.update(eq(id), any(EquipoRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/equipos/equipos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreEquipo").value("G2 Esports Renovado"));
    }

    @Test
    @DisplayName("DELETE /api/v1/equipos/equipos/{id} - Debería eliminar el equipo y retornar 204 No Content")
    void shouldDeleteEquipo() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/v1/equipos/equipos/{id}", id))
                .andExpect(status().isNoContent());
    }
}
