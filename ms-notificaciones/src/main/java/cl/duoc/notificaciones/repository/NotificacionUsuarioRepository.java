package cl.duoc.notificaciones.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.notificaciones.model.NotificacionUsuario;

@Repository
public interface NotificacionUsuarioRepository extends JpaRepository<NotificacionUsuario, Integer> {
    List<NotificacionUsuario> findByIdUsuario(Integer idUsuario);
    List<NotificacionUsuario> findByIdUsuarioAndLeida(Integer idUsuario, Boolean leida);
    void deleteByIdUsuario(Integer idUsuario);
}