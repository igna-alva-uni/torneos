package cl.duoc.juegos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.juegos.dto.JuegoRequest;
import cl.duoc.juegos.dto.JuegoResponse;
import cl.duoc.juegos.exception.JuegoNotFoundException;
import cl.duoc.juegos.mapper.JuegoMapper;
import cl.duoc.juegos.model.Genero;
import cl.duoc.juegos.model.Juegos;
import cl.duoc.juegos.model.Plataformas;
import cl.duoc.juegos.repository.GeneroRepository;
import cl.duoc.juegos.repository.JuegoRepository;
import cl.duoc.juegos.repository.PlataformaRepository;

@ExtendWith(MockitoExtension.class)
class JuegoServiceTest {

    @Mock
    private JuegoRepository juegoRepository;

    @Mock
    private GeneroRepository generoRepository;

    @Mock
    private PlataformaRepository plataformaRepository;

    @Mock
    private JuegoMapper juegoMapper;

    @InjectMocks
    private JuegoService juegoService;

    @Test
    @DisplayName("Debería obtener todos los juegos")
    void shouldFindAllJuegos() {
        Juegos j1 = new Juegos(); j1.setId(1);
        List<Juegos> list = Arrays.asList(j1);

        JuegoResponse r1 = new JuegoResponse(); r1.setId(1);
        List<JuegoResponse> expected = Arrays.asList(r1);

        when(juegoRepository.findAllWithPlataformas()).thenReturn(list);
        when(juegoMapper.toResponseList(list)).thenReturn(expected);

        List<JuegoResponse> actual = juegoService.findAll();

        assertNotNull(actual);
        assertEquals(1, actual.size());
    }

    @Test
    @DisplayName("Debería obtener juego por ID")
    void shouldFindJuegoById() {
        Juegos j = new Juegos(); j.setId(1);
        JuegoResponse r = new JuegoResponse(); r.setId(1);

        when(juegoRepository.findById(1)).thenReturn(Optional.of(j));
        when(juegoMapper.toResponse(j)).thenReturn(r);

        JuegoResponse actual = juegoService.findById(1);

        assertNotNull(actual);
        assertEquals(1, actual.getId());
    }

    @Test
    @DisplayName("Debería lanzar exception si el juego no existe")
    void shouldThrowExceptionWhenJuegoNotFound() {
        when(juegoRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(JuegoNotFoundException.class, () -> juegoService.findById(99));
    }

    @Test
    @DisplayName("Debería crear un juego exitosamente")
    void shouldCreateJuegoSuccessfully() {
        JuegoRequest request = JuegoRequest.builder()
                .nombre("CS:GO")
                .idGenero(1)
                .plataformas(new HashSet<>(Arrays.asList(1, 2)))
                .descripcion("Shooter")
                .build();

        Genero genero = new Genero(); genero.setId(1);
        Plataformas p1 = new Plataformas(); p1.setId(1);
        Plataformas p2 = new Plataformas(); p2.setId(2);

        Juegos juegoSinId = new Juegos();
        juegoSinId.setNombre("CS:GO");

        Juegos juegoGuardado = new Juegos();
        juegoGuardado.setId(1);
        juegoGuardado.setNombre("CS:GO");

        JuegoResponse response = new JuegoResponse();
        response.setId(1);
        response.setNombre("CS:GO");

        when(generoRepository.findById(1)).thenReturn(Optional.of(genero));
        when(plataformaRepository.findAllById(request.getPlataformas())).thenReturn(Arrays.asList(p1, p2));
        when(juegoMapper.toModel(request)).thenReturn(juegoSinId);
        when(juegoRepository.save(juegoSinId)).thenReturn(juegoGuardado);
        when(juegoMapper.toResponse(juegoGuardado)).thenReturn(response);

        JuegoResponse actual = juegoService.create(request);

        assertNotNull(actual);
        assertEquals("CS:GO", actual.getNombre());
        verify(juegoRepository, times(1)).save(juegoSinId);
    }
}
