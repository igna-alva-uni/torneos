package cl.duoc.estadisticas.repository;

import cl.duoc.estadisticas.model.EstadisticaPartida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadisticaPartidaRepository extends JpaRepository<EstadisticaPartida, Integer> {
    
    Optional<EstadisticaPartida> findByIdEstadisticaPartida(Integer idEstadisticaPartida);
 
    boolean existsByIdPartida(Integer idPartida);
}