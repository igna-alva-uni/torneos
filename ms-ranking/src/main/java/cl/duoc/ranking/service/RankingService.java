package cl.duoc.ranking.service;

import cl.duoc.ranking.dto.ranking.RankingRequest;
import cl.duoc.ranking.dto.ranking.RankingResponse;
import cl.duoc.ranking.mapper.RankingMapper;
import cl.duoc.ranking.mapper.RegistroRankingMapper;
import cl.duoc.ranking.model.Ranking;
import cl.duoc.ranking.model.RegistroRanking;
import cl.duoc.ranking.model.TipoRanking;
import cl.duoc.ranking.repository.RankingRepository;
import cl.duoc.ranking.event.RankingEventProducer;
import cl.duoc.commons.event.RankingUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository repository;
    private final RankingMapper mapper;
    private final RegistroRankingMapper registroMapper;
    private final TipoRankingService tipoRankingService;
    private final RankingEventProducer eventProducer;

    @Transactional(readOnly = true)
    public List<RankingResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RankingResponse findById(Integer id) {
        Ranking ranking = repository.findByIdRanking(id)
                .orElseThrow(() -> new RuntimeException("Ranking no encontrado con ID: " + id));
        return mapper.toResponse(ranking);
    }

    @Transactional
    public RankingResponse create(RankingRequest request) {
        TipoRanking tipoRanking = tipoRankingService.findEntityById(request.getIdTipoRanking());

        Ranking ranking = mapper.toEntity(request);
        ranking.setTipoRanking(tipoRanking);

        Ranking savedRanking = repository.save(ranking);
        return mapper.toResponse(savedRanking);
    }

    @Transactional
    public RankingResponse update(Integer id, RankingRequest request) {
        Ranking existingRanking = repository.findByIdRanking(id)
                .orElseThrow(() -> new RuntimeException("Ranking no encontrado con ID: " + id));
        
        if (!existingRanking.getTipoRanking().getIdTipoRanking().equals(request.getIdTipoRanking())) {
            TipoRanking nuevoTipo = tipoRankingService.findEntityById(request.getIdTipoRanking());
            existingRanking.setTipoRanking(nuevoTipo);
        }
        
        existingRanking.setIdJuego(request.getIdJuego());
        
        existingRanking.getRegistros().clear();
        if (request.getRegistros() != null) {
            List<RegistroRanking> nuevosRegistros = request.getRegistros().stream()
                    .map(registroMapper::toEntity)
                    .collect(Collectors.toList());
            
            nuevosRegistros.forEach(registro -> registro.setRanking(existingRanking));
            existingRanking.getRegistros().addAll(nuevosRegistros);
        }
        
        Ranking updatedRanking = repository.save(existingRanking);

        eventProducer.sendRankingUpdateEvent(RankingUpdateEvent.builder()
            .tipoRanking(existingRanking.getTipoRanking().getIdTipoRanking())
            .idEquipo(existingRanking.getIdRanking())
            .puntos(existingRanking.getRegistros().size())
            .build());

        return mapper.toResponse(updatedRanking);
    }
    @Transactional
    public void delete(Integer id) {
        Ranking ranking = repository.findByIdRanking(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar: El Ranking no existe."));
        repository.delete(ranking);
    }
}