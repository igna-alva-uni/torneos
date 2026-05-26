package cl.duoc.estadisticas.repository;

import cl.duoc.estadisticas.model.EstadisticaEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadisticaEquipoRepository extends JpaRepository<EstadisticaEquipo, Integer> {
    
    Optional<EstadisticaEquipo> findByIdEstadisticaEquipo(Integer idEstadisticaEquipo);
    
    boolean existsByIdEquipo(Integer idEquipo);
}