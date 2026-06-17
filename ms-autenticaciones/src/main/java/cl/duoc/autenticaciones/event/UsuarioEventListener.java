package cl.duoc.autenticaciones.event;

import cl.duoc.autenticaciones.event.UsuarioEliminadoEvent;
import cl.duoc.autenticaciones.repository.AuthUserRepository;
import lombok.AllArgsConstructor;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UsuarioEventListener {

    private final AuthUserRepository repo;

    @KafkaListener(topics = "usuarios-eliminados", groupId = "ms-autenticaciones")
    public void onUsuarioEliminado(UsuarioEliminadoEvent evento) {
        System.out.println("ms-autenticaciones recibió el evento de eliminacion del usuario con id : " + evento.getUsuarioId());
        repo.findById(evento.getUsuarioId()).ifPresent(toDel -> repo.deleteById(toDel.getId()));
    }
}