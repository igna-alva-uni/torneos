package cl.duoc.usuarios.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duoc.usuarios.model.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
    Optional<Pais> findByCodigoPais(String codigoPais);
    Optional<Pais> findByNombrePais(String nombrePais);
}