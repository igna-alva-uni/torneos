package cl.duoc.inscripciones.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.commons.event.InscripcionCreadaEvent;
import cl.duoc.inscripciones.client.TorneosClient;
import cl.duoc.inscripciones.client.UsuarioClient;
import cl.duoc.inscripciones.dto.InscripcionRequest;
import cl.duoc.inscripciones.kafka.InscripcionProducer;
import cl.duoc.inscripciones.model.Inscripcion;
import cl.duoc.inscripciones.repository.InscripcionRepository;

@ExtendWith(MockitoExtension.class)
class InscripcionServiceTest {

    @Mock
    private InscripcionRepository inscripcionRepository;

    @Mock
    private InscripcionProducer inscripcionProducer;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private TorneosClient torneosClient;

    @InjectMocks
    private InscripcionService inscripcionService;

    @Test
    @DisplayName("Debería registrar una inscripción exitosamente")
    void shouldCreateInscripcionSuccessfully() {
        InscripcionRequest request = new InscripcionRequest();
        request.setIdUsuario(1L);
        request.setIdTorneo(2L);

        Inscripcion saved = Inscripcion.builder()
                .idInscripcion(10L)
                .idUsuario(1L)
                .idTorneo(2L)
                .estado("PENDIENTE")
                .fechaInscripcion(LocalDateTime.now())
                .build();

        when(usuarioClient.getUsuarioById(1L)).thenReturn(null);
        when(torneosClient.getTorneoById(2L)).thenReturn(null);
        when(inscripcionRepository.save(any(Inscripcion.class))).thenReturn(saved);

        Inscripcion actual = inscripcionService.crear(request);

        assertNotNull(actual);
        assertEquals(10L, actual.getIdInscripcion());
        assertEquals("PENDIENTE", actual.getEstado());
        verify(inscripcionRepository, times(1)).save(any(Inscripcion.class));
        verify(inscripcionProducer, times(1)).enviarInscripcionCreada(any(InscripcionCreadaEvent.class));
    }

    @Test
    @DisplayName("Debería lanzar exception si el usuario no existe")
    void shouldThrowExceptionWhenUsuarioNotFound() {
        InscripcionRequest request = new InscripcionRequest();
        request.setIdUsuario(1L);
        request.setIdTorneo(2L);

        when(usuarioClient.getUsuarioById(1L)).thenThrow(new RuntimeException("Usuario no encontrado"));

        assertThrows(IllegalArgumentException.class, () -> inscripcionService.crear(request));
        verify(inscripcionRepository, never()).save(any());
    }
}
