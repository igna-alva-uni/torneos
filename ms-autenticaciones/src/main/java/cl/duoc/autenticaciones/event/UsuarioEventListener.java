package cl.duoc.autenticaciones.event;

import cl.duoc.commons.event.UsuarioEliminadoEvent;
import cl.duoc.commons.event.UsuarioActualizadoEvent;
import cl.duoc.autenticaciones.repository.AuthUserRepository;
import lombok.AllArgsConstructor;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class UsuarioEventListener {

    private final AuthUserRepository repo;

    @KafkaListener(topics = "usuarios-eliminados", groupId = "ms-autenticaciones")
    @Transactional
    public void onUsuarioEliminado(UsuarioEliminadoEvent evento) {
        System.out.println("ms-autenticaciones recibió el evento de eliminacion del usuario con id : " + evento.getUsuarioId());
        repo.findById(evento.getUsuarioId()).ifPresent(toDel -> repo.deleteById(toDel.getId()));
    }

    @KafkaListener(topics = "usuarios-actualizados", groupId = "ms-autenticaciones")
    @Transactional
    public void onUsuarioActualizado(UsuarioActualizadoEvent evento) {
        System.out.println("ms-autenticaciones recibió el evento de actualización del usuario con id : " + evento.getUsuarioId());
        repo.findById(evento.getUsuarioId()).ifPresent(user -> {
            user.setEmail(evento.getEmail());
            repo.save(user);
        });
    }
}