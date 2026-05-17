package cl.duoc.equipos.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duoc.equipos.model.MiembroEquipo;

@Repository
public interface MiembroEquipoRepository extends JpaRepository<MiembroEquipo, Long> {
    Optional<MiembroEquipo> findByIdUsuario(Long idUsuario);
    List<MiembroEquipo> findByEquipoId(Long idEquipo);
}