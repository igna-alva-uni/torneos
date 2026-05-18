package cl.duoc.torneos.repository;

import cl.duoc.torneos.model.Formato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormatoRepository extends JpaRepository<Formato,Integer> {
}
