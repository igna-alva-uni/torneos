package cl.duoc.ranking.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ranking.model.RegistroRanking;

@Repository
public interface RegistroRankingRepository extends JpaRepository<RegistroRanking, Integer> {
    Optional<RegistroRanking> findByIdRegistroRanking(Integer idRegistroRanking);
}   