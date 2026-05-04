package cl.duoc.juegos.repository;

import java.util.Optional;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.juegos.model.Juegos;

@Repository
public interface JuegosRepository extends JpaRepository<Juegos, Integer>{
    Optional<Juegos> findByGenero(String genero);
    boolean existsByGenero(@NotBlank String genero);
}
