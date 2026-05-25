package cl.duoc.inscripciones.kafka;

import cl.duoc.inscripciones.dto.InscripcionCreadaEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InscripcionProducer {

    private static final String TOPIC = "inscripcion-creada";

    private final KafkaTemplate<String, InscripcionCreadaEvent> kafkaTemplate;

    public void enviarInscripcionCreada(InscripcionCreadaEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}