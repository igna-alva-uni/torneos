package cl.duoc.ranking.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.ranking.dto.ranking.RankingRequest;
import cl.duoc.ranking.dto.ranking.RankingResponse;
import cl.duoc.ranking.mapper.RankingMapper;
import cl.duoc.ranking.mapper.RegistroRankingMapper;
import cl.duoc.ranking.model.Ranking;
import cl.duoc.ranking.model.TipoRanking;
import cl.duoc.ranking.repository.RankingRepository;
import cl.duoc.ranking.event.RankingEventProducer;

@ExtendWith(MockitoExtension.class)
class RankingServiceTest {

    @Mock
    private RankingRepository repository;

    @Mock
    private RankingMapper mapper;

    @Mock
    private RegistroRankingMapper registroMapper;

    @Mock
    private TipoRankingService tipoRankingService;

    @Mock
    private RankingEventProducer eventProducer;

    @InjectMocks
    private RankingService rankingService;

    @Test
    @DisplayName("Debería retornar todos los rankings")
    void shouldFindAllRankings() {
        Ranking r = new Ranking();
        r.setIdRanking(1);
        List<Ranking> list = Arrays.asList(r);

        RankingResponse response = new RankingResponse();
        response.setIdRanking(1);

        when(repository.findAll()).thenReturn(list);
        when(mapper.toResponse(r)).thenReturn(response);

        List<RankingResponse> actual = rankingService.findAll();

        assertNotNull(actual);
        assertEquals(1, actual.size());
    }

    @Test
    @DisplayName("Debería crear un ranking exitosamente")
    void shouldCreateRankingSuccessfully() {
        RankingRequest request = RankingRequest.builder()
                .idJuego(5)
                .idTipoRanking(1)
                .registros(new ArrayList<>())
                .build();

        TipoRanking tipoRanking = new TipoRanking();
        tipoRanking.setIdTipoRanking(1);

        Ranking rankingSinId = new Ranking();
        rankingSinId.setIdJuego(5);

        Ranking rankingGuardado = new Ranking();
        rankingGuardado.setIdRanking(1);
        rankingGuardado.setIdJuego(5);
        rankingGuardado.setTipoRanking(tipoRanking);

        RankingResponse response = new RankingResponse();
        response.setIdRanking(1);
        response.setIdJuego(5);

        when(tipoRankingService.findEntityById(1)).thenReturn(tipoRanking);
        when(mapper.toEntity(request)).thenReturn(rankingSinId);
        when(repository.save(rankingSinId)).thenReturn(rankingGuardado);
        when(mapper.toResponse(rankingGuardado)).thenReturn(response);

        RankingResponse actual = rankingService.create(request);

        assertNotNull(actual);
        assertEquals(1, actual.getIdRanking());
        verify(repository, times(1)).save(rankingSinId);
    }
}
