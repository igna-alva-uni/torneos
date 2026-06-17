package cl.duoc.estadisticas.service;

import cl.duoc.commons.client.PartidaClient;
import cl.duoc.estadisticas.dto.partida.EstadisticaPartidaRequest;
import cl.duoc.estadisticas.dto.partida.EstadisticaPartidaResponse;
import cl.duoc.estadisticas.mapper.EstadisticaPartidaMapper;
import cl.duoc.estadisticas.model.EstadisticaPartida;
import cl.duoc.estadisticas.repository.EstadisticaPartidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstadisticaPartidaService {

    private final EstadisticaPartidaRepository repository;
    private final EstadisticaPartidaMapper mapper;
    private final PartidaClient partidaClient;

    @Transactional(readOnly = true)
    public List<EstadisticaPartidaResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EstadisticaPartidaResponse findById(Integer id) {
        EstadisticaPartida entity = repository.findByIdEstadisticaPartida(id)
                .orElseThrow(() -> new RuntimeException("Estadística de partida no encontrada con ID: " + id));
        return mapper.toResponse(entity);
    }

    @Transactional
    public EstadisticaPartidaResponse create(EstadisticaPartidaRequest request) {
        // Validar partida
        try {
            partidaClient.getPartidaById(request.getIdPartida());
        } catch (Exception e) {
            throw new IllegalArgumentException("No existe la partida con ID: " + request.getIdPartida());
        }

        if (repository.existsByIdPartida(request.getIdPartida())) {
            throw new RuntimeException("Ya existen estadísticas registradas para la partida con ID: " + request.getIdPartida());
        }
        EstadisticaPartida entity = mapper.toEntity(request);
        EstadisticaPartida saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional
    public EstadisticaPartidaResponse update(Integer id, EstadisticaPartidaRequest request) {
        EstadisticaPartida existing = repository.findByIdEstadisticaPartida(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar: Registro no encontrado con ID: " + id));
        
        // Validar partida
        try {
            partidaClient.getPartidaById(request.getIdPartida());
        } catch (Exception e) {
            throw new IllegalArgumentException("No existe la partida con ID: " + request.getIdPartida());
        }

        if (!existing.getIdPartida().equals(request.getIdPartida()) && repository.existsByIdPartida(request.getIdPartida())) {
            throw new RuntimeException("Ya existen estadísticas para la nueva partida con ID: " + request.getIdPartida());
        }
        
        existing.setIdPartida(request.getIdPartida());
        existing.setDuracion(request.getDuracion());
        
        EstadisticaPartida updated = repository.save(existing);
        return mapper.toResponse(updated);
    }

    @Transactional
    public void delete(Integer id) {
        EstadisticaPartida entity = repository.findByIdEstadisticaPartida(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar: Registro no encontrado con ID: " + id));
        repository.delete(entity);
    }
}