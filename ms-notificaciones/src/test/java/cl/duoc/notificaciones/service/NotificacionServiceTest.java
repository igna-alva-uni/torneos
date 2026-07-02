package cl.duoc.notificaciones.service;

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

import cl.duoc.notificaciones.dto.TipoNotificacionRequestDTO;
import cl.duoc.notificaciones.dto.TipoNotificacionResponseDTO;
import cl.duoc.notificaciones.exception.NotificacionException;
import cl.duoc.notificaciones.mapper.NotificacionMapper;
import cl.duoc.notificaciones.model.TipoNotificacion;
import cl.duoc.notificaciones.repository.NotificacionRepository;
import cl.duoc.notificaciones.repository.NotificacionUsuarioRepository;
import cl.duoc.notificaciones.repository.TipoNotificacionRepository;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {

    @Mock
    private TipoNotificacionRepository tipoRepository;

    @Mock
    private NotificacionRepository notificacionRepository;

    @Mock
    private NotificacionUsuarioRepository notificacionUsuarioRepository;

    @Mock
    private NotificacionMapper mapper;

    @InjectMocks
    private NotificacionService notificacionService;

    @Test
    @DisplayName("Debería crear un tipo de notificación exitosamente")
    void shouldCreateTipoSuccessfully() {
        TipoNotificacionRequestDTO request = new TipoNotificacionRequestDTO();
        request.setNombreTipoNotificacion("EMAIL");

        TipoNotificacion saved = new TipoNotificacion();
        saved.setIdTipoNotificacion(1);
        saved.setNombreTipoNotificacion("EMAIL");

        TipoNotificacionResponseDTO response = new TipoNotificacionResponseDTO();
        response.setIdTipoNotificacion(1);
        response.setNombreTipoNotificacion("EMAIL");

        when(tipoRepository.save(any(TipoNotificacion.class))).thenReturn(saved);
        when(mapper.toTipoResponse(saved)).thenReturn(response);

        TipoNotificacionResponseDTO actual = notificacionService.crearTipo(request);

        assertNotNull(actual);
        assertEquals(1, actual.getIdTipoNotificacion());
        assertEquals("EMAIL", actual.getNombreTipoNotificacion());
        verify(tipoRepository, times(1)).save(any(TipoNotificacion.class));
    }

    @Test
    @DisplayName("Debería lanzar exception al crear tipo si el nombre está vacío")
    void shouldThrowExceptionWhenNombreIsEmpty() {
        TipoNotificacionRequestDTO request = new TipoNotificacionRequestDTO();
        request.setNombreTipoNotificacion("");

        assertThrows(NotificacionException.class, () -> notificacionService.crearTipo(request));
        verify(tipoRepository, never()).save(any());
    }
}
