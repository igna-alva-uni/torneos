package cl.duoc.juegos.repository;

import cl.duoc.juegos.model.Plataformas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlataformaRepository extends JpaRepository<Plataformas,Integer> {
}
