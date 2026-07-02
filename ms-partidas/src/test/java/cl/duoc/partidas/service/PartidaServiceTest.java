package cl.duoc.partidas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.partidas.client.EquipoClient;
import cl.duoc.partidas.client.TorneosClient;
import cl.duoc.partidas.dto.PartidaRequest;
import cl.duoc.partidas.dto.PartidaResponse;
import cl.duoc.partidas.mapper.PartidaMapper;
import cl.duoc.partidas.mapper.ResultadoPartidaMapper;
import cl.duoc.partidas.model.EstadoPartida;
import cl.duoc.partidas.model.Partida;
import cl.duoc.partidas.repository.PartidaRepository;

@ExtendWith(MockitoExtension.class)
class PartidaServiceTest {

    @Mock
    private PartidaRepository partidaRepository;

    @Mock
    private PartidaMapper partidaMapper;

    @Mock
    private ResultadoPartidaMapper resultadoMapper;

    @Mock
    private TorneosClient torneosClient;

    @Mock
    private EquipoClient equipoClient;

    @InjectMocks
    private PartidaService partidaService;

    @Test
    @DisplayName("Debería obtener todas las partidas")
    void shouldFindAllPartidas() {
        Partida p = new Partida(); p.setId(1);
        List<Partida> list = Arrays.asList(p);

        PartidaResponse r = new PartidaResponse(); r.setId(1);
        List<PartidaResponse> expected = Arrays.asList(r);

        when(partidaRepository.findAll()).thenReturn(list);
        when(partidaMapper.toResponseList(list)).thenReturn(expected);

        List<PartidaResponse> actual = partidaService.findAll();

        assertNotNull(actual);
        assertEquals(1, actual.size());
    }

    @Test
    @DisplayName("Debería crear una partida exitosamente")
    void shouldCreatePartidaSuccessfully() {
        PartidaRequest request = PartidaRequest.builder()
                .torneoId(10)
                .ronda("Ronda 1")
                .estado(EstadoPartida.PENDIENTE)
                .build();

        Partida partidaSinId = new Partida();
        partidaSinId.setTorneoId(10);

        Partida partidaGuardada = new Partida();
        partidaGuardada.setId(1);
        partidaGuardada.setTorneoId(10);

        PartidaResponse response = new PartidaResponse();
        response.setId(1);
        response.setTorneoId(10);

        when(torneosClient.getTorneo(10)).thenReturn(null); // Simula que el torneo existe
        when(partidaMapper.toModel(request)).thenReturn(partidaSinId);
        when(partidaRepository.save(partidaSinId)).thenReturn(partidaGuardada);
        when(partidaMapper.toResponse(partidaGuardada)).thenReturn(response);

        PartidaResponse actual = partidaService.create(request);

        assertNotNull(actual);
        assertEquals(1, actual.getId());
        verify(partidaRepository, times(1)).save(partidaSinId);
    }
}
