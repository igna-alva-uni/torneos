package cl.duoc.estadisticas.event;

import cl.duoc.commons.event.UsuarioEliminadoEvent;
import cl.duoc.commons.event.UsuarioCreadoEvent;
import cl.duoc.estadisticas.model.Usuario;
import cl.duoc.estadisticas.repository.EstadisticaJugadorRepository;
import cl.duoc.estadisticas.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class UsuarioEventListener {

    private final EstadisticaJugadorRepository repository;
    private final UsuarioRepository usuarioRepository;

    @KafkaListener(topics = "usuarios-creados", groupId = "ms-estadisticas")
    @Transactional
    public void onUsuarioCreado(UsuarioCreadoEvent evento) {
        System.out.println("ms-estadisticas recibió el evento de creación del usuario con id : " + evento.getUsuarioId());
        usuarioRepository.save(new Usuario(evento.getUsuarioId()));
    }

    @KafkaListener(topics = "usuarios-eliminados", groupId = "ms-estadisticas")
    @Transactional
    public void onUsuarioEliminado(UsuarioEliminadoEvent evento) {
        System.out.println("ms-estadisticas recibió el evento de eliminacion del usuario con id : " + evento.getUsuarioId());
        repository.deleteByIdUsuario(evento.getUsuarioId().intValue());
        usuarioRepository.deleteById(evento.getUsuarioId());
    }
}
