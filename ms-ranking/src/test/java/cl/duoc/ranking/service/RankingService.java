package cl.duoc.ranking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.ranking.dto.RankingRequest;
import cl.duoc.ranking.dto.RankingResponse;
import cl.duoc.ranking.mapper.RankingMapper;
import cl.duoc.ranking.model.Ranking;
import cl.duoc.ranking.repository.RankingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;
    private final RankingMapper rankingMapper;

    

}
