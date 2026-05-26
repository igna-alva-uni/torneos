package cl.duoc.partidas.repository;

import cl.duoc.partidas.model.ResultadoPartida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultadoPartidaRepository extends JpaRepository<ResultadoPartida,Integer> {
    Optional<ResultadoPartida> findByPartidaId(Integer idPartida);
}
