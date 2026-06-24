package cl.duoc.usuarios.event;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic topicUserDeleted(){
        log.debug("publicando usuario eliminado");
        return TopicBuilder.name("usuarios-eliminados").partitions(1).replicas(1).build();
    }


}
