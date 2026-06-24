package cl.duoc.notificaciones.kafka;

import cl.duoc.notificaciones.dto.InscripcionCreadaEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InscripcionConsumer {

    @KafkaListener(
            topics = "inscripcion-creada",
            groupId = "notificaciones-group"
    )
    public void recibirInscripcionCreada(InscripcionCreadaEvent event) {

        System.out.println("===== EVENTO RECIBIDO DESDE KAFKA =====");
        System.out.println("ID inscripción: " + event.getIdInscripcion());
        System.out.println("ID usuario: " + event.getIdUsuario());
        System.out.println("ID torneo: " + event.getIdTorneo());
        System.out.println("Mensaje: " + event.getMensaje());
    }
}