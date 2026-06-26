package cl.duoc.inscripciones.event;

import cl.duoc.commons.event.UsuarioEliminadoEvent;
import cl.duoc.commons.event.UsuarioCreadoEvent;
import cl.duoc.inscripciones.model.Usuario;
import cl.duoc.inscripciones.repository.InscripcionRepository;
import cl.duoc.inscripciones.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class UsuarioEventListener {

    private final InscripcionRepository repository;
    private final UsuarioRepository usuarioRepository;

    @KafkaListener(topics = "usuarios-creados", groupId = "ms-inscripciones")
    @Transactional
    public void onUsuarioCreado(UsuarioCreadoEvent evento) {
        System.out.println("ms-inscripciones recibió el evento de creación del usuario con id : " + evento.getUsuarioId());
        usuarioRepository.save(new Usuario(evento.getUsuarioId()));
    }

    @KafkaListener(topics = "usuarios-eliminados", groupId = "ms-inscripciones")
    @Transactional
    public void onUsuarioEliminado(UsuarioEliminadoEvent evento) {
        System.out.println("ms-inscripciones recibió el evento de eliminacion del usuario con id : " + evento.getUsuarioId());
        repository.deleteByIdUsuario(evento.getUsuarioId());
        usuarioRepository.deleteById(evento.getUsuarioId());
    }
}
