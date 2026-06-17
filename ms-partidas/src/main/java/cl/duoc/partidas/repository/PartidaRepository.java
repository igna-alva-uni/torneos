package cl.duoc.partidas.repository;

import cl.duoc.partidas.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidaRepository extends JpaRepository<Partida,Integer> {
    List<Partida> findByTorneoId(Integer torneoId);
}
