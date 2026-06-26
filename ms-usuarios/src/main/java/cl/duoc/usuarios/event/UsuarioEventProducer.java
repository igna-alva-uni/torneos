package cl.duoc.usuarios.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import cl.duoc.commons.event.UsuarioEliminadoEvent;
import cl.duoc.commons.event.UsuarioCreadoEvent;
import cl.duoc.commons.event.UsuarioActualizadoEvent;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publicarUsuarioEliminado(UsuarioEliminadoEvent evento) {
        kafkaTemplate.send("usuarios-eliminados", evento);
        System.out.println("Evento eliminado enviado a Kafka: " + evento.getUsername());
    }

    public void publicarUsuarioCreado(UsuarioCreadoEvent evento) {
        kafkaTemplate.send("usuarios-creados", evento);
        System.out.println("Evento creado enviado a Kafka: " + evento.getUsername());
    }

    public void publicarUsuarioActualizado(UsuarioActualizadoEvent evento){
        kafkaTemplate.send("usuarios-actualizados", evento);
        System.out.println("Evento actualizado enviado a Kafka: " + evento.getUsername());
    }
}
