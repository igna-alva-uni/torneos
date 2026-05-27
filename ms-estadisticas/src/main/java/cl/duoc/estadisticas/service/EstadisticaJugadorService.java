package cl.duoc.estadisticas.service;

import cl.duoc.estadisticas.client.UserClient;
import cl.duoc.estadisticas.dto.jugador.EstadisticaJugadorRequest;
import cl.duoc.estadisticas.dto.jugador.EstadisticaJugadorResponse;
import cl.duoc.estadisticas.mapper.EstadisticaJugadorMapper;
import cl.duoc.estadisticas.model.EstadisticaJugador;
import cl.duoc.estadisticas.repository.EstadisticaJugadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstadisticaJugadorService {

    private final EstadisticaJugadorRepository repository;
    private final EstadisticaJugadorMapper mapper;
    private final UserClient userClient;

    @Transactional(readOnly = true)
    public List<EstadisticaJugadorResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EstadisticaJugadorResponse findById(Integer id) {
        EstadisticaJugador entity = repository.findByIdEstadisticaJugador(id)
                .orElseThrow(() -> new RuntimeException("Estadística de jugador no encontrada con ID: " + id));
        return mapper.toResponse(entity);
    }

    @Transactional
    public EstadisticaJugadorResponse create(EstadisticaJugadorRequest request) {
        // Validar usuario
        try {
            userClient.getUsuarioById(request.getIdUsuario().longValue());
        } catch (Exception e) {
            throw new IllegalArgumentException("No existe el usuario con ID: " + request.getIdUsuario());
        }

        if (repository.existsByIdUsuario(request.getIdUsuario())) {
            throw new RuntimeException("Ya existen estadísticas registradas para el usuario con ID: " + request.getIdUsuario());
        }
        EstadisticaJugador entity = mapper.toEntity(request);
        EstadisticaJugador saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional
    public EstadisticaJugadorResponse update(Integer id, EstadisticaJugadorRequest request) {
        EstadisticaJugador existing = repository.findByIdEstadisticaJugador(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar: Registro no encontrado con ID: " + id));
        
        // Validar usuario
        try {
            userClient.getUsuarioById(request.getIdUsuario().longValue());
        } catch (Exception e) {
            throw new IllegalArgumentException("No existe el usuario con ID: " + request.getIdUsuario());
        }

        if (!existing.getIdUsuario().equals(request.getIdUsuario()) && repository.existsByIdUsuario(request.getIdUsuario())) {
            throw new RuntimeException("Ya existen estadísticas para el nuevo usuario con ID: " + request.getIdUsuario());
        }
        
        existing.setIdUsuario(request.getIdUsuario());
        existing.setVictoriasJugador(request.getVictoriasJugador());
        existing.setDerrotasJugador(request.getDerrotasJugador());
        
        EstadisticaJugador updated = repository.save(existing);
        return mapper.toResponse(updated);
    }

    @Transactional
    public void delete(Integer id) {
        EstadisticaJugador entity = repository.findByIdEstadisticaJugador(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar: Registro no encontrado con ID: " + id));
        repository.delete(entity);
    }
}