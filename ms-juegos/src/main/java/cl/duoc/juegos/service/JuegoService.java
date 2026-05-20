package cl.duoc.juegos.service;

import cl.duoc.juegos.dto.JuegoRequest;
import cl.duoc.juegos.dto.JuegoResponse;
import cl.duoc.juegos.exception.JuegoDuplicadoException;
import cl.duoc.juegos.exception.JuegoNotFoundException;
import cl.duoc.juegos.mapper.JuegoMapper;
import cl.duoc.juegos.model.Genero;
import cl.duoc.juegos.model.Juegos;
import cl.duoc.juegos.model.Plataforma;
import cl.duoc.juegos.repository.GeneroRepository;
import cl.duoc.juegos.repository.JuegoRepository;
import cl.duoc.juegos.repository.PlataformaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JuegoService {

    private final JuegoRepository juegoRepository;
    private final GeneroRepository generoRepository;
    private final PlataformaRepository plataformaRepository;
    private final JuegoMapper juegoMapper;

    public List<JuegoResponse> findAll() {
        return juegoMapper.toResponseList(juegoRepository.findAll());
    }

    public JuegoResponse findById(Integer id) {
        Juegos juego = juegoRepository.findById(id)
                .orElseThrow(() -> new JuegoNotFoundException(id));

        return juegoMapper.toResponse(juego);
    }

    public JuegoResponse create(JuegoRequest request) {

        if (juegoRepository.existsByNombre(request.getNombre())) {
            throw new JuegoDuplicadoException(request.getNombre());
        }

        Genero genero = generoRepository.findById(request.getIdGenero())
                .orElseThrow(() -> new RuntimeException("Género no encontrado"));

        Set<Plataforma> plataformas = new HashSet<>(
                plataformaRepository.findAllById(request.getPlataformasIds())
        );

        Juegos juego = juegoMapper.toModel(request);
        juego.setGenero(genero);
        juego.setPlataformas(plataformas);

        return juegoMapper.toResponse(juegoRepository.save(juego));
    }

    public JuegoResponse update(Integer id, JuegoRequest request) {

        Juegos juego = juegoRepository.findById(id)
                .orElseThrow(() -> new JuegoNotFoundException(id));

        Genero genero = generoRepository.findById(request.getIdGenero())
                .orElseThrow(() -> new RuntimeException("Género no encontrado"));

        Set<Plataforma> plataformas = new HashSet<>(
                plataformaRepository.findAllById(request.getPlataformasIds())
        );

        juego.setNombre(request.getNombre());
        juego.setGenero(genero);
        juego.setDescripcion(request.getDescripcion());
        juego.setPlataformas(plataformas);

        return juegoMapper.toResponse(juegoRepository.save(juego));
    }

    public void delete(Integer id) {
        Juegos juego = juegoRepository.findById(id)
                .orElseThrow(() -> new JuegoNotFoundException(id));

        juegoRepository.delete(juego);
    }
}
