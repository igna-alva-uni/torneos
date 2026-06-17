package cl.duoc.equipos.event;

import cl.duoc.equipos.event.UsuarioEliminadoEvent;
import cl.duoc.equipos.repository.MiembroEquipoRepository;
import lombok.AllArgsConstructor;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UsuarioEventListener {

    private final MiembroEquipoRepository repo;

    @KafkaListener(topics = "usuarios-eliminados", groupId = "ms-equipos")
    public void onUsuarioEliminado(UsuarioEliminadoEvent evento) {
        System.out.println("ms-equipos recibió el evento de eliminacion del usuario con id : " + evento.getUsuarioId());
        repo.findByIdUsuario(evento.getUsuarioId()).ifPresent(toDel -> repo.deleteById(toDel.getId()));

    }
}