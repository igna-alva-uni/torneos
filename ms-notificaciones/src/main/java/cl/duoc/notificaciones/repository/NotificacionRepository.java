package cl.duoc.notificaciones.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duoc.notificaciones.model.Notificacion;
import cl.duoc.notificaciones.model.TipoNotificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    Optional<Notificacion> findByTipo(TipoNotificacion tipo);

    
}