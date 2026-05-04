package cl.duoc.torneos.repository;

import cl.duoc.torneos.model.Formato;
import cl.duoc.torneos.model.Torneos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TorneosRepository extends JpaRepository<Torneos, Integer> {
    Optional<Torneos> findByVideojuego(String videojuego);
    boolean existsByVideojuego(String videojuego);

    Optional<Object> findById(Long id);
    void deleteById(Long id);
}
