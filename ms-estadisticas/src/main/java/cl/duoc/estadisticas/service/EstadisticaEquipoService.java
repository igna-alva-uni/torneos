package cl.duoc.estadisticas.service;

import cl.duoc.estadisticas.dto.equipo.EstadisticaEquipoRequest;
import cl.duoc.estadisticas.dto.equipo.EstadisticaEquipoResponse;
import cl.duoc.estadisticas.mapper.EstadisticaEquipoMapper;
import cl.duoc.estadisticas.model.EstadisticaEquipo;
import cl.duoc.estadisticas.repository.EstadisticaEquipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstadisticaEquipoService {

    private final EstadisticaEquipoRepository repository;
    private final EstadisticaEquipoMapper mapper;

    @Transactional(readOnly = true)
    public List<EstadisticaEquipoResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EstadisticaEquipoResponse findById(Integer id) {
        EstadisticaEquipo entity = repository.findByIdEstadisticaEquipo(id)
                .orElseThrow(() -> new RuntimeException("Estadística de equipo no encontrada con ID: " + id));
        return mapper.toResponse(entity);
    }

    @Transactional
    public EstadisticaEquipoResponse create(EstadisticaEquipoRequest request) {
        if (repository.existsByIdEquipo(request.getIdEquipo())) {
            throw new RuntimeException("Ya existen estadísticas registradas para el equipo con ID: " + request.getIdEquipo());
        }
        EstadisticaEquipo entity = mapper.toEntity(request);
        EstadisticaEquipo saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional
    public EstadisticaEquipoResponse update(Integer id, EstadisticaEquipoRequest request) {
        EstadisticaEquipo existing = repository.findByIdEstadisticaEquipo(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar: Registro no encontrado con ID: " + id));
        
        if (!existing.getIdEquipo().equals(request.getIdEquipo()) && repository.existsByIdEquipo(request.getIdEquipo())) {
            throw new RuntimeException("Ya existen estadísticas para el nuevo equipo con ID: " + request.getIdEquipo());
        }
        
        existing.setIdEquipo(request.getIdEquipo());
        existing.setVictoriasEquipo(request.getVictoriasEquipo());
        existing.setDerrotasEquipo(request.getDerrotasEquipo());
        
        EstadisticaEquipo updated = repository.save(existing);
        return mapper.toResponse(updated);
    }

    @Transactional
    public void delete(Integer id) {
        EstadisticaEquipo entity = repository.findByIdEstadisticaEquipo(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar: Registro no encontrado con ID: " + id));
        repository.delete(entity);
    }
}