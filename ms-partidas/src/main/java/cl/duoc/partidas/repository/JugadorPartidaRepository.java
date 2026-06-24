package cl.duoc.partidas.repository;

import cl.duoc.partidas.model.JugadorPartida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorPartidaRepository extends JpaRepository<JugadorPartida,Integer> {
}
