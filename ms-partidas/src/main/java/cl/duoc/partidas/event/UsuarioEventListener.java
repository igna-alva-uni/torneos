package cl.duoc.partidas.event;

import cl.duoc.commons.event.UsuarioEliminadoEvent;
import cl.duoc.commons.event.UsuarioCreadoEvent;
import cl.duoc.partidas.model.Usuario;
import cl.duoc.partidas.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class UsuarioEventListener {

    private final UsuarioRepository usuarioRepository;

    @KafkaListener(topics = "usuarios-creados", groupId = "ms-partidas")
    @Transactional
    public void onUsuarioCreado(UsuarioCreadoEvent evento) {
        System.out.println("ms-partidas recibió el evento de creación del usuario con id : " + evento.getUsuarioId());
        usuarioRepository.save(new Usuario(evento.getUsuarioId()));
    }

    @KafkaListener(topics = "usuarios-eliminados", groupId = "ms-partidas")
    @Transactional
    public void onUsuarioEliminado(UsuarioEliminadoEvent evento) {
        System.out.println("ms-partidas recibió el evento de eliminacion del usuario con id : " + evento.getUsuarioId());
        usuarioRepository.deleteById(evento.getUsuarioId());
    }
}
