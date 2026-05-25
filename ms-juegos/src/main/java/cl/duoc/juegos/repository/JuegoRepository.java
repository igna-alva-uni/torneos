package cl.duoc.juegos.repository;

import cl.duoc.juegos.model.Juegos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JuegoRepository extends JpaRepository<Juegos,Integer> {
    @Query("SELECT j FROM Juegos j LEFT JOIN FETCH j.plataformas")
    List<Juegos> findAllWithPlataformas();
}
