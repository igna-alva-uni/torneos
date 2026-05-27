package cl.duoc.estadisticas.event;

import cl.duoc.estadisticas.repository.EstadisticaJugadorRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class UsuarioEventListener {

    private final EstadisticaJugadorRepository repository;

    @KafkaListener(topics = "usuarios-eliminados", groupId = "ms-estadisticas")
    @Transactional
    public void onUsuarioEliminado(UsuarioEliminadoEvent evento) {
        System.out.println("ms-estadisticas recibió el evento de eliminacion del usuario con id : " + evento.getUsuarioId());
        repository.deleteByIdUsuario(evento.getUsuarioId().intValue());
    }
}
