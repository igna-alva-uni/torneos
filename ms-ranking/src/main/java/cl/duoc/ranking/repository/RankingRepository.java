package cl.duoc.ranking.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ranking.model.Ranking;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Integer>{
    Optional<Ranking> findByIdRanking(Integer idRanking);
}
