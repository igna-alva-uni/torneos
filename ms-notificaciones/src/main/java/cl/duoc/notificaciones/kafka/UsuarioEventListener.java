package cl.duoc.notificaciones.kafka;

import cl.duoc.commons.event.UsuarioEliminadoEvent;
import cl.duoc.commons.event.UsuarioCreadoEvent;
import cl.duoc.notificaciones.model.Usuario;
import cl.duoc.notificaciones.repository.NotificacionUsuarioRepository;
import cl.duoc.notificaciones.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UsuarioEventListener {

    private final NotificacionUsuarioRepository repository;
    private final UsuarioRepository usuarioRepository;

    @KafkaListener(topics = "usuarios-creados", groupId = "ms-notificaciones")
    @Transactional
    public void onUsuarioCreado(UsuarioCreadoEvent evento) {
        System.out.println("ms-notificaciones recibió el evento de creación del usuario con id : " + evento.getUsuarioId());
        usuarioRepository.save(new Usuario(evento.getUsuarioId()));
    }

    @KafkaListener(topics = "usuarios-eliminados", groupId = "ms-notificaciones")
    @Transactional
    public void onUsuarioEliminado(UsuarioEliminadoEvent evento) {
        System.out.println("ms-notificaciones recibió el evento de eliminacion del usuario con id : " + evento.getUsuarioId());
        repository.deleteByIdUsuario(evento.getUsuarioId().intValue());
        usuarioRepository.deleteById(evento.getUsuarioId());
    }
}
