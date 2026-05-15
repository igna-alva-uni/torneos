package cl.duoc.estadisticas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duoc.estadisticas.model.EstadisticaJugador;
import java.util.Optional;

@Repository
public interface EstadisticaJugadorRepository extends JpaRepository<EstadisticaJugador, Integer> {
    Optional<EstadisticaJugador> findByIdUsuario(Integer idUsuario);
}