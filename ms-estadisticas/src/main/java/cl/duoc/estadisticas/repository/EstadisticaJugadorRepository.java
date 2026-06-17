package cl.duoc.estadisticas.repository;

import cl.duoc.estadisticas.model.EstadisticaJugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadisticaJugadorRepository extends JpaRepository<EstadisticaJugador, Integer> {
    
    Optional<EstadisticaJugador> findByIdEstadisticaJugador(Integer idEstadisticaJugador);
    
    boolean existsByIdUsuario(Integer idUsuario);

    void deleteByIdUsuario(Integer idUsuario);
}