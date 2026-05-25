package cl.duoc.juegos.repository;

import cl.duoc.juegos.model.Juegos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JuegoRepository extends JpaRepository<Juegos,Integer> {
    boolean existsByNombre(String nombre);
    List<Juegos> findByGeneroId(Integer idGenero);
}
