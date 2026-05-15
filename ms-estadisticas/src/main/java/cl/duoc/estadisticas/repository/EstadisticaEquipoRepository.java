package cl.duoc.estadisticas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duoc.estadisticas.model.EstadisticaEquipo;
import java.util.Optional;

@Repository
public interface EstadisticaEquipoRepository extends JpaRepository<EstadisticaEquipo, Integer> {
    Optional<EstadisticaEquipo> findByIdEquipo(Integer idEquipo);
}