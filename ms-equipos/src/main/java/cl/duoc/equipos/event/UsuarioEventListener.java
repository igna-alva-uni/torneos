package cl.duoc.equipos.event;

import cl.duoc.commons.event.UsuarioEliminadoEvent;
import cl.duoc.commons.event.UsuarioCreadoEvent;
import cl.duoc.equipos.model.Usuario;
import cl.duoc.equipos.repository.MiembroEquipoRepository;
import cl.duoc.equipos.repository.UsuarioRepository;
import lombok.AllArgsConstructor;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class UsuarioEventListener {

    private final MiembroEquipoRepository repo;
    private final UsuarioRepository usuarioRepository;

    @KafkaListener(topics = "usuarios-creados", groupId = "ms-equipos")
    @Transactional
    public void onUsuarioCreado(UsuarioCreadoEvent evento) {
        System.out.println("ms-equipos recibió el evento de creación del usuario con id : " + evento.getUsuarioId());
        usuarioRepository.save(new Usuario(evento.getUsuarioId()));
    }

    @KafkaListener(topics = "usuarios-eliminados", groupId = "ms-equipos")
    @Transactional
    public void onUsuarioEliminado(UsuarioEliminadoEvent evento) {
        System.out.println("ms-equipos recibió el evento de eliminacion del usuario con id : " + evento.getUsuarioId());
        repo.findByIdUsuario(evento.getUsuarioId()).ifPresent(toDel -> repo.deleteById(toDel.getId()));
        usuarioRepository.deleteById(evento.getUsuarioId());
    }
}