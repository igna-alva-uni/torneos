package cl.duoc.autenticaciones.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import cl.duoc.autenticaciones.model.AuthUser;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByEmail(String email);
}