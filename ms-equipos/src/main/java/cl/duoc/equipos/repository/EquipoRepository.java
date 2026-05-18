package cl.duoc.equipos.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duoc.equipos.model.Equipo;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    Optional<Equipo> findByNombreEquipo(String nombreEquipo);
}