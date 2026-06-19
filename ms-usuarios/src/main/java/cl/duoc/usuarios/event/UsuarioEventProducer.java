package cl.duoc.usuarios.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import cl.duoc.commons.event.UsuarioEliminadoEvent;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "usuarios-eliminados";

    public void publicarUsuarioEliminado(UsuarioEliminadoEvent evento) {
        kafkaTemplate.send(TOPIC, evento);
        System.out.println("Evento enviado a Kafka: " + evento.getUsername());
    }
    public void publicarUsuarioActualizado(UsuarioActualizadoEvent evento){
        kafkaTemplate.send(TOPIC, evento);
        System.out.println("Evento enviado a Kafka: " + evento.getUsername());
    }
}
