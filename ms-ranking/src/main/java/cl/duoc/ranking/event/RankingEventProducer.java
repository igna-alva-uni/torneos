package cl.duoc.ranking.event;

import cl.duoc.ranking.event.RankingUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RankingEventProducer {
    private static final String TOPIC_NAME = "ranking-updates";
    

    private final KafkaTemplate<String, RankingUpdateEvent> kafkaTemplate;

    public void sendRankingUpdateEvent(RankingUpdateEvent event) {
        if (Objects.isNull(event)) {
            log.warn("Intento de enviar un evento nulo. Operación cancelada.");
            return;
        }
        log.info("Enviando evento de actualización de ranking: {}", event);
        kafkaTemplate.send(TOPIC_NAME, event);
    }


}
