package cl.duoc.torneos.repository;

import cl.duoc.torneos.model.Torneos;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TorneosRepository extends JpaRepository<Torneos,Integer> {
    Optional<Torneos> findByJuego(String videojuego);
    boolean existsByJuego(@NotBlank String videojuego);
}
