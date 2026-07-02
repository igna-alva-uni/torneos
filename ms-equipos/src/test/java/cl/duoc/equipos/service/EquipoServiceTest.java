package cl.duoc.equipos.service;

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

import cl.duoc.equipos.dtos.equipo.EquipoRequest;
import cl.duoc.equipos.dtos.equipo.EquipoResponse;
import cl.duoc.equipos.mapper.EquipoMapper;
import cl.duoc.equipos.model.Equipo;
import cl.duoc.equipos.repository.EquipoRepository;

@ExtendWith(MockitoExtension.class)
class EquipoServiceTest {

    @Mock
    private EquipoRepository equipoRepo;

    @Mock
    private EquipoMapper mapper;

    @InjectMocks
    private EquipoService equipoService;

    @Test
    @DisplayName("Debería agregar un equipo exitosamente cuando el nombre no existe")
    void shouldAddEquipoSuccessfully() {
        EquipoRequest request = new EquipoRequest();
        request.setNombreEquipo("Team Liquid");

        Equipo equipoSinId = new Equipo();
        equipoSinId.setNombreEquipo("Team Liquid");

        Equipo equipoGuardado = new Equipo();
        equipoGuardado.setId(1L);
        equipoGuardado.setNombreEquipo("Team Liquid");

        EquipoResponse expectedResponse = new EquipoResponse();
        expectedResponse.setId(1L);
        expectedResponse.setNombreEquipo("Team Liquid");

        when(equipoRepo.findByNombreEquipo("Team Liquid")).thenReturn(Optional.empty());
        when(mapper.toModel(request)).thenReturn(equipoSinId);
        when(equipoRepo.save(equipoSinId)).thenReturn(equipoGuardado);
        when(mapper.toResponse(equipoGuardado)).thenReturn(expectedResponse);

        EquipoResponse actualResponse = equipoService.addEquipo(request);

        assertNotNull(actualResponse);
        assertEquals(1L, actualResponse.getId());
        assertEquals("Team Liquid", actualResponse.getNombreEquipo());

        verify(equipoRepo, times(1)).findByNombreEquipo("Team Liquid");
        verify(equipoRepo, times(1)).save(equipoSinId);
    }

    @Test
    @DisplayName("Debería lanzar IllegalArgumentException cuando el nombre del equipo ya existe")
    void shouldThrowExceptionWhenEquipoNameAlreadyExists() {
        EquipoRequest request = new EquipoRequest();
        request.setNombreEquipo("Team Liquid");

        Equipo equipoExistente = new Equipo();
        equipoExistente.setId(1L);
        equipoExistente.setNombreEquipo("Team Liquid");

        when(equipoRepo.findByNombreEquipo("Team Liquid")).thenReturn(Optional.of(equipoExistente));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            equipoService.addEquipo(request);
        });

        assertEquals("ese nombre ya pertenece a un equipo", exception.getMessage());
        verify(equipoRepo, never()).save(any(Equipo.class));
    }

    @Test
    @DisplayName("Debería obtener todos los equipos")
    void shouldReturnAllEquipos() {
        Equipo eq1 = new Equipo(); eq1.setId(1L); eq1.setNombreEquipo("Team A");
        Equipo eq2 = new Equipo(); eq2.setId(2L); eq2.setNombreEquipo("Team B");
        List<Equipo> listaEquipos = Arrays.asList(eq1, eq2);

        EquipoResponse resp1 = new EquipoResponse(); resp1.setId(1L); resp1.setNombreEquipo("Team A");
        EquipoResponse resp2 = new EquipoResponse(); resp2.setId(2L); resp2.setNombreEquipo("Team B");
        List<EquipoResponse> expectedResponses = Arrays.asList(resp1, resp2);

        when(equipoRepo.findAll()).thenReturn(listaEquipos);
        when(mapper.toResponseList(listaEquipos)).thenReturn(expectedResponses);

        List<EquipoResponse> actualResponses = equipoService.getAll();

        assertNotNull(actualResponses);
        assertEquals(2, actualResponses.size());
        assertEquals("Team A", actualResponses.get(0).getNombreEquipo());
    }

    @Test
    @DisplayName("Debería obtener un equipo por su ID")
    void shouldGetEquipoById() {
        Long id = 1L;
        Equipo equipo = new Equipo();
        equipo.setId(id);
        equipo.setNombreEquipo("Fnatic");

        EquipoResponse expectedResponse = new EquipoResponse();
        expectedResponse.setId(id);
        expectedResponse.setNombreEquipo("Fnatic");

        when(equipoRepo.findById(id)).thenReturn(Optional.of(equipo));
        when(mapper.toResponse(equipo)).thenReturn(expectedResponse);

        EquipoResponse response = equipoService.getById(id);

        assertNotNull(response);
        assertEquals("Fnatic", response.getNombreEquipo());
    }

    @Test
    @DisplayName("Debería actualizar un equipo exitosamente")
    void shouldUpdateEquipoSuccessfully() {
        Long id = 1L;
        EquipoRequest request = new EquipoRequest();
        request.setNombreEquipo("Fnatic Editado");

        Equipo equipoExistente = new Equipo();
        equipoExistente.setId(id);
        equipoExistente.setNombreEquipo("Fnatic");

        Equipo equipoGuardado = new Equipo();
        equipoGuardado.setId(id);
        equipoGuardado.setNombreEquipo("Fnatic Editado");

        EquipoResponse expectedResponse = new EquipoResponse();
        expectedResponse.setId(id);
        expectedResponse.setNombreEquipo("Fnatic Editado");

        when(equipoRepo.findById(id)).thenReturn(Optional.of(equipoExistente));
        when(equipoRepo.findByNombreEquipo("Fnatic Editado")).thenReturn(Optional.empty());
        when(equipoRepo.save(equipoExistente)).thenReturn(equipoGuardado);
        when(mapper.toResponse(equipoGuardado)).thenReturn(expectedResponse);

        EquipoResponse actualResponse = equipoService.update(id, request);

        assertNotNull(actualResponse);
        assertEquals("Fnatic Editado", actualResponse.getNombreEquipo());
        verify(equipoRepo, times(1)).save(equipoExistente);
    }

    @Test
    @DisplayName("Debería eliminar un equipo si existe el ID")
    void shouldDeleteEquipoSuccessfully() {
        Long id = 1L;
        when(equipoRepo.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> equipoService.delete(id));

        verify(equipoRepo, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Debería lanzar RuntimeException al eliminar si el ID no existe")
    void shouldThrowExceptionWhenDeletingNonExistentId() {
        Long id = 999L;
        when(equipoRepo.existsById(id)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            equipoService.delete(id);
        });

        assertEquals("no hay ningun equipo con ese id", exception.getMessage());
        verify(equipoRepo, never()).deleteById(anyLong());
    }
}
