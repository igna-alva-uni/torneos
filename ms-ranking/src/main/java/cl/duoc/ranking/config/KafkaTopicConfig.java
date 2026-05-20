package cl.duoc.ranking.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic rankingUpdatesTopic() {
        log.debug("****************************************");
        log.debug("****************************************");
        log.info("Creando tópico Kafka: ranking-updates");
        return TopicBuilder.name("ranking-updates")
                .partitions(1)
                .replicas(1)
                .build();
        
    }
}
