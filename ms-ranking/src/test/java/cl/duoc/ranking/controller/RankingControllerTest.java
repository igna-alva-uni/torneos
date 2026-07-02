package cl.duoc.ranking.controller;

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

import cl.duoc.ranking.dto.ranking.RankingRequest;
import cl.duoc.ranking.dto.ranking.RankingResponse;
import cl.duoc.ranking.service.RankingService;
import cl.duoc.ranking.service.RegistroRankingService;
import cl.duoc.ranking.service.TipoRankingService;

@WebMvcTest(RankingController.class)
@AutoConfigureMockMvc(addFilters = false)
class RankingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RankingService rankingService;

    @MockBean
    private TipoRankingService tipoRankingService;

    @MockBean
    private RegistroRankingService registroRankingService;

    @MockBean
    private cl.duoc.commons.security.JwtTokenProvider jwtTokenProvider;

    @MockBean
    private cl.duoc.commons.security.JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    @DisplayName("GET /api/v1/rankings/rankings - Debería retornar todos los rankings")
    void shouldReturnAllRankings() throws Exception {
        RankingResponse r = new RankingResponse();
        r.setIdRanking(1);
        r.setIdJuego(5);

        when(rankingService.findAll()).thenReturn(Collections.singletonList(r));

        mockMvc.perform(get("/api/v1/rankings/rankings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idRanking").value(1));
    }

    @Test
    @DisplayName("POST /api/v1/rankings/rankings - Debería crear un ranking")
    void shouldCreateRanking() throws Exception {
        RankingRequest request = RankingRequest.builder()
                .idJuego(5)
                .idTipoRanking(1)
                .build();

        RankingResponse r = new RankingResponse();
        r.setIdRanking(1);
        r.setIdJuego(5);

        when(rankingService.create(any(RankingRequest.class))).thenReturn(r);

        mockMvc.perform(post("/api/v1/rankings/rankings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idRanking").value(1));
    }
}
