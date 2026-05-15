package cl.duoc.estadisticas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duoc.estadisticas.model.EstadisticaPartida;
import java.util.Optional;

@Repository
public interface EstadisticaPartidaRepository extends JpaRepository<EstadisticaPartida, Integer> {
    Optional<EstadisticaPartida> findByIdPartida(Integer idPartida);
}