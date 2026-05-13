package cl.duoc.ranking.service;

import cl.duoc.ranking.dto.RankingRequest;
import cl.duoc.ranking.dto.RankingResponse;
import cl.duoc.ranking.exception.RankingNotFoundException;
import cl.duoc.ranking.exception.TipoDuplicadoException;
import cl.duoc.ranking.model.Ranking;
import cl.duoc.ranking.model.TipoRanking;
import cl.duoc.ranking.model.RegistroRanking;
import cl.duoc.ranking.repository.RankingRepository;
import cl.duoc.ranking.mapper.RankingMapper;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;
    private final RankingMapper rankingMapper;

    public List<RankingResponse> findAll() {
        return rankingMapper.toResponseList(rankingRepository.findAll());
    }

    public RankingResponse findById(int id) {
        Ranking ranking = rankingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ranking no encontrado con ID: " + id));
        return rankingMapper.toResponse(ranking);
    }

    @Transactional
    public RankingResponse create(RankingRequest request){
        if (rankingRepository.existsByTipo(request.getTipoRanking())){
            String rankingExistente = rankingRepository.findByTipo(request.getTipoRanking())
            .map(ranking -> ranking.getTipoRanking().getNombreTipoRanking())
            .orElse("Desconocido");
            throw new TipoDuplicadoException(request.getTipoRanking(), rankingExistente);
        }

        Ranking ranking = rankingMapper.toModel(request);
        ranking.setTipoRanking(tipo); 

        if (ranking == null){
            throw new IllegalArgumentException("La solicitud del ranking no pudo ser procesada.");
        }

        Ranking guardado = rankingRepository.save(ranking);
        return rankingMapper.toResponse(guardado);
    }

    @Transactional
    public RankingResponse update(int id, RankingRequest request){
        Ranking existente = rankingRepository.findById(id)
        .orElseThrow(()-> new RankingNotFoundException(id));
        
        if (!existente.getTipoRanking().getNombreTipoRanking().equalsIgnoreCase(request.getTipoRanking())){
            if (rankingRepository.existsByTipo(request.getTipoRanking())) {
                rankingRepository.findByTipo(request.getTipoRanking()).ifPresent(ranking ->{
                    throw new TipoDuplicadoException(request.getTipoRanking(), ranking.getTipoRanking().getNombreTipoRanking());
                });
            }
        }
        
        existente.setTipoRanking(request.getTipoRanking());
        existente.setRegistroRanking(request.getRegistroRanking());
        existente.setRankings(request.getRankings());

        Ranking guardado = rankingRepository.save(existente);
        return rankingMapper.toResponse(guardado);
    }
    @Transactional
    public void delete(int id) {
        if (!rankingRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Ranking no encontrado");
        }
        rankingRepository.deleteById(id);
    }
}