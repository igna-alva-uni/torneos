package cl.duoc.ranking.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.ranking.model.TipoRanking;

@Repository
public interface TipoRankingRepository extends JpaRepository<TipoRanking, Integer>{
    Optional<TipoRanking> findByIdTipoRanking(Integer idTipoRanking);
    Optional<TipoRanking> findByNombreTipoRanking(String nombreTipoRanking);
    boolean existsByTipo(String nombreTipoRanking);
}
