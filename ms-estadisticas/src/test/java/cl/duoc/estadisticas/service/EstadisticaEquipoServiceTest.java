package cl.duoc.estadisticas.service;

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

import cl.duoc.estadisticas.client.TeamClient;
import cl.duoc.estadisticas.dto.equipo.EstadisticaEquipoRequest;
import cl.duoc.estadisticas.dto.equipo.EstadisticaEquipoResponse;
import cl.duoc.estadisticas.mapper.EstadisticaEquipoMapper;
import cl.duoc.estadisticas.model.EstadisticaEquipo;
import cl.duoc.estadisticas.repository.EstadisticaEquipoRepository;

@ExtendWith(MockitoExtension.class)
class EstadisticaEquipoServiceTest {

    @Mock
    private EstadisticaEquipoRepository repository;

    @Mock
    private EstadisticaEquipoMapper mapper;

    @Mock
    private TeamClient teamClient;

    @InjectMocks
    private EstadisticaEquipoService service;

    @Test
    @DisplayName("Debería retornar todas las estadísticas de equipos")
    void shouldFindAllStats() {
        EstadisticaEquipo eq = new EstadisticaEquipo();
        eq.setIdEstadisticaEquipo(1);
        List<EstadisticaEquipo> list = Arrays.asList(eq);

        EstadisticaEquipoResponse response = new EstadisticaEquipoResponse();
        response.setIdEstadisticaEquipo(1);

        when(repository.findAll()).thenReturn(list);
        when(mapper.toResponse(eq)).thenReturn(response);

        List<EstadisticaEquipoResponse> actual = service.findAll();

        assertNotNull(actual);
        assertEquals(1, actual.size());
    }

    @Test
    @DisplayName("Debería crear estadísticas de equipo exitosamente")
    void shouldCreateStatsSuccessfully() {
        EstadisticaEquipoRequest request = EstadisticaEquipoRequest.builder()
                .idEquipo(5)
                .victoriasEquipo(10)
                .derrotasEquipo(2)
                .build();

        EstadisticaEquipo entitySinId = new EstadisticaEquipo();
        entitySinId.setIdEquipo(5);

        EstadisticaEquipo savedEntity = new EstadisticaEquipo();
        savedEntity.setIdEstadisticaEquipo(1);
        savedEntity.setIdEquipo(5);

        EstadisticaEquipoResponse response = new EstadisticaEquipoResponse();
        response.setIdEstadisticaEquipo(1);
        response.setIdEquipo(5);

        when(teamClient.getEquipoById(5L)).thenReturn(null); // Simula que el equipo existe
        when(repository.existsByIdEquipo(5)).thenReturn(false);
        when(mapper.toEntity(request)).thenReturn(entitySinId);
        when(repository.save(entitySinId)).thenReturn(savedEntity);
        when(mapper.toResponse(savedEntity)).thenReturn(response);

        EstadisticaEquipoResponse actual = service.create(request);

        assertNotNull(actual);
        assertEquals(1, actual.getIdEstadisticaEquipo());
        verify(repository, times(1)).save(entitySinId);
    }
}
