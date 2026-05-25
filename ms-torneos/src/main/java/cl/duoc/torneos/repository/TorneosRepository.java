package cl.duoc.torneos.repository;

import cl.duoc.torneos.model.Torneos;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TorneosRepository extends JpaRepository<Torneos,Integer> {
    List<Torneos> findByIdJuego(Integer idJuego);
    boolean existsByNombre(String nombre);
    boolean existsByNombreAndIdJuego(String nombre, Integer idJuego);
}
