package cl.duoc.autenticaciones.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import cl.duoc.autenticaciones.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNombreRol(String nombreRol);
}