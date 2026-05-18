package cl.duoc.ranking.service;

import cl.duoc.ranking.dto.RankingRequest;
import cl.duoc.ranking.dto.RankingResponse;
import cl.duoc.ranking.exception.RankingNotFoundException;
import cl.duoc.ranking.exception.TipoDuplicadoException;
import cl.duoc.ranking.model.Ranking;
import cl.duoc.ranking.model.TipoRanking;
import cl.duoc.ranking.repository.RankingRepository;
import cl.duoc.ranking.repository.TipoRankingRepository;
import cl.duoc.ranking.repository.RegistroRankingRepository;
import cl.duoc.ranking.mapper.RankingMapper;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor

public class RankingService {

    private final TipoRankingRepository tipoRankingRepository;
    private final RankingRepository rankingRepository;
    private final RankingMapper rankingMapper;

    public List<RankingResponse> findAll() {
        return rankingMapper.toResponseList(rankingRepository.findAll());
    }

    public RankingResponse findById(int id) {
        Ranking ranking = rankingRepository.findById(id)
                .orElseThrow(() -> new RankingNotFoundException(id));
        return rankingMapper.toResponse(ranking);
    }

    public RankingResponse findByIdTipoRanking(Integer idTipoRanking) {
        TipoRanking tipoRanking = tipoRankingRepository.findByIdTipoRanking(idTipoRanking)
                .orElseThrow(() -> new RankingNotFoundException(idTipoRanking));
        return rankingMapper.toResponse(tipoRanking);
    }

    @Transactional
    public RankingResponse create(RankingRequest request) {
        if (tipoRankingRepository.existsByTipo(request.getTipoRanking())) {
            String nombreExistente = tipoRankingRepository.findByNombreTipoRanking(request.getTipoRanking())
            .map(r -> r.getNombreTipoRanking()) 
            .orElse("Desconocido");
        }

        Ranking ranking = rankingMapper.toModel(request);
        
        if (ranking == null) {
            throw new IllegalArgumentException("La solicitud del ranking no pudo ser procesada.");
        }

        Ranking guardado = rankingRepository.save(ranking);
        return rankingMapper.toResponse(guardado);
    }

    @Transactional
    public RankingResponse update(int id, RankingRequest request) {
        Ranking existente = rankingRepository.findById(id)
                .orElseThrow(() -> new RankingNotFoundException(id));
        
        if (!existente.getTipoRanking().getNombreTipoRanking().equalsIgnoreCase(request.getTipoRanking())) {
            if (tipoRankingRepository.existsByTipo(request.getTipoRanking())) {
                throw new TipoDuplicadoException(request.getTipoRanking(), request.getTipoRanking());
            }
        }
        
        
        rankingMapper.updateModelFromDto(request, existente);

        Ranking guardado = rankingRepository.save(existente);
        return rankingMapper.toResponse(guardado);
    }

    @Transactional
    public void delete(int id) {
        if (!rankingRepository.existsById(id)) {
            throw new RankingNotFoundException(id);
        }
        rankingRepository.deleteById(id);
    }
}