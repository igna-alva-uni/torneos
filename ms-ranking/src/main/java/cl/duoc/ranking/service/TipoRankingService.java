package cl.duoc.ranking.service;

import cl.duoc.ranking.dto.tipoRanking.TipoRankingRequest;
import cl.duoc.ranking.dto.tipoRanking.TipoRankingResponse;
import cl.duoc.ranking.mapper.TipoRankingMapper;
import cl.duoc.ranking.model.TipoRanking;
import cl.duoc.ranking.repository.TipoRankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TipoRankingService {

    private final TipoRankingRepository repository;
    private final TipoRankingMapper mapper;

    @Transactional(readOnly = true)
    public List<TipoRankingResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

        
    @Transactional(readOnly = true)
    public TipoRankingResponse findById(Integer id) {
        TipoRanking entity = findEntityById(id);
        return mapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public TipoRanking findEntityById(Integer id) {
        return repository.findByIdTipoRanking(id)
                .orElseThrow(() -> new RuntimeException("Tipo de Ranking no encontrado con ID: " + id));
    }

    @Transactional
    public TipoRankingResponse create(TipoRankingRequest request) {
        if (repository.existsByNombreTipoRanking(request.getNombreTipoRanking())) {
            throw new RuntimeException("El tipo de ranking '" + request.getNombreTipoRanking() + "' ya existe.");
        }
        
        TipoRanking entity = mapper.toEntity(request);
        TipoRanking savedEntity = repository.save(entity);
        return mapper.toResponse(savedEntity);
    }

    @Transactional
    public TipoRankingResponse update(Integer id, TipoRankingRequest request) {
        TipoRanking existingEntity = findEntityById(id);
        
  
        if (!existingEntity.getNombreTipoRanking().equals(request.getNombreTipoRanking()) 
                && repository.existsByNombreTipoRanking(request.getNombreTipoRanking())) {
            throw new RuntimeException("El tipo de ranking '" + request.getNombreTipoRanking() + "' ya existe.");
        }
        
        existingEntity.setNombreTipoRanking(request.getNombreTipoRanking());
        TipoRanking updatedEntity = repository.save(existingEntity);
        return mapper.toResponse(updatedEntity);
    }

    @Transactional
    public void delete(Integer id) {
        TipoRanking entity = findEntityById(id);
        repository.delete(entity);
    }
}