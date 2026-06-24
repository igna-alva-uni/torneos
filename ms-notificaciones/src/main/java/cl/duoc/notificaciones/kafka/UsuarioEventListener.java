package cl.duoc.notificaciones.kafka;

import cl.duoc.notificaciones.dto.UsuarioEliminadoEvent;
import cl.duoc.notificaciones.repository.NotificacionUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UsuarioEventListener {

    private final NotificacionUsuarioRepository repository;

    @KafkaListener(topics = "usuarios-eliminados", groupId = "ms-notificaciones")
    @Transactional
    public void onUsuarioEliminado(UsuarioEliminadoEvent evento) {
        System.out.println("ms-notificaciones recibió el evento de eliminacion del usuario con id : " + evento.getUsuarioId());
        repository.deleteByIdUsuario(evento.getUsuarioId().intValue());
    }
}
