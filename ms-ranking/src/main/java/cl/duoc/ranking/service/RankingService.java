package cl.duoc.ranking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.ranking.dto.RankingRequest;
import cl.duoc.ranking.dto.RankingResponse;
import cl.duoc.ranking.mapper.RankingMapper;
import cl.duoc.ranking.exception.RankingNotFoundException;
import cl.duoc.ranking.exception.TipoDuplicadoException;
import cl.duoc.ranking.model.Ranking;
import cl.duoc.ranking.repository.RankingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;
    private final RankingMapper rankingMapper;

    public List<RankingResponse> findAll(){
        return rankingMapper.toResponseList(rankingRepository.findAll());
    }

    public RankingResponse findById(int id){
        Ranking ranking = rankingRepository.findById(id)
        .orElseThrow(()-> new RankingNotFoundException(id));
        return rankingMapper.toResponse(ranking);
    }

    public RankingResponse findByTipo(String tipoRanking){
        Ranking ranking = rankingRepository.findByTipo(tipoRanking)
            .orElseThrow(()-> new RankingNotFoundException(tipoRanking));
        return rankingMapper.toResponse(ranking);
    }
     
     public RankingResponse create(RankingRequest request){
        if (rankingRepository.existsByTipo(request.getTipoRanking())){
            String tipoExistente = rankingRepository.findByTipo(request.getTipoRanking())
            .map(Ranking::getTipoRanking)
            .orElse("Desconocido");
            throw new TipoDuplicadoException(request.getTipoRanking(), tipoExistente);
        }

        Ranking ranking = rankingMapper.toModel(request);

        if (ranking == null){
            throw new IllegalArgumentException("La solicitud del ranking no pudo ser procesada.");
        }

        Ranking guardado = rankingRepository.save(ranking);
        return rankingMapper.toResponse(guardado);
    }

}
