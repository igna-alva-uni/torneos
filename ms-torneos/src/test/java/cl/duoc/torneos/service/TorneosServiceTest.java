package cl.duoc.torneos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.torneos.client.JuegosClient;
import cl.duoc.torneos.dto.TorneosRequest;
import cl.duoc.torneos.dto.TorneosResponse;
import cl.duoc.torneos.exception.TorneosDuplicadoException;
import cl.duoc.torneos.exception.TorneosNotFoundException;
import cl.duoc.torneos.mapper.TorneosMapper;
import cl.duoc.torneos.model.Formato;
import cl.duoc.torneos.model.Torneos;
import cl.duoc.torneos.repository.FormatoRepository;
import cl.duoc.torneos.repository.TorneosRepository;

@ExtendWith(MockitoExtension.class)
class TorneosServiceTest {

    @Mock
    private TorneosRepository torneosRepository;

    @Mock
    private FormatoRepository formatoRepository;

    @Mock
    private TorneosMapper torneosMapper;

    @Mock
    private JuegosClient juegosClient;

    @InjectMocks
    private TorneosService torneosService;

    @Test
    @DisplayName("Debería retornar todos los torneos")
    void shouldFindAllTorneos() {
        Torneos t1 = new Torneos(); t1.setId(1);
        List<Torneos> list = Arrays.asList(t1);

        TorneosResponse r1 = new TorneosResponse(); r1.setId(1);
        List<TorneosResponse> expected = Arrays.asList(r1);

        when(torneosRepository.findAll()).thenReturn(list);
        when(torneosMapper.toResponseList(list)).thenReturn(expected);

        List<TorneosResponse> actual = torneosService.findAll();

        assertNotNull(actual);
        assertEquals(1, actual.size());
    }

    @Test
    @DisplayName("Debería lanzar TorneosNotFoundException si no hay torneos")
    void shouldThrowExceptionWhenNoTorneosExist() {
        when(torneosRepository.findAll()).thenReturn(Arrays.asList());
        assertThrows(TorneosNotFoundException.class, () -> torneosService.findAll());
    }

    @Test
    @DisplayName("Debería crear un torneo exitosamente")
    void shouldCreateTorneoSuccessfully() {
        TorneosRequest request = new TorneosRequest(1, 1);
        request.setNombre("Torneo CS:GO");
        request.setFechaInicio(LocalDate.now());

        Formato formato = new Formato();
        formato.setId(1);

        Torneos torneoSinId = new Torneos();
        torneoSinId.setNombre("Torneo CS:GO");

        Torneos torneoGuardado = new Torneos();
        torneoGuardado.setId(1);
        torneoGuardado.setNombre("Torneo CS:GO");
        torneoGuardado.setIdJuego(1);

        TorneosResponse response = new TorneosResponse();
        response.setId(1);
        response.setNombre("Torneo CS:GO");
        response.setIdJuego(1);

        when(juegosClient.getJuegos(1)).thenReturn(null); // Simula que el juego existe
        when(torneosRepository.existsByNombreAndIdJuego("Torneo CS:GO", 1)).thenReturn(false);
        when(formatoRepository.findById(1)).thenReturn(Optional.of(formato));
        when(torneosMapper.toModel(request)).thenReturn(torneoSinId);
        when(torneosRepository.save(torneoSinId)).thenReturn(torneoGuardado);
        when(torneosMapper.toResponse(torneoGuardado)).thenReturn(response);

        TorneosResponse actual = torneosService.create(request);

        assertNotNull(actual);
        assertEquals("Torneo CS:GO", actual.getNombre());
        verify(torneosRepository, times(1)).save(torneoSinId);
    }
}
