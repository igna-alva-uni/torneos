package cl.duoc.estadisticas.event;

import cl.duoc.commons.event.RankingUpdateEvent;
import cl.duoc.estadisticas.repository.EstadisticaPartidaRepository;
import cl.duoc.estadisticas.repository.EstadisticaJugadorRepository;
import cl.duoc.estadisticas.service.EstadisticaEquipoService;
import cl.duoc.estadisticas.repository.EstadisticaEquipoRepository;
import lombok.AllArgsConstructor;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RankingEventListener {

    private final EstadisticaEquipoRepository equipoRepo;
    private final EstadisticaEquipoService service;


    @KafkaListener(topics = "ranking-updates", groupId = "ms-ranking")
    public void onRankingUpdate(RankingUpdateEvent evento) {
        System.out.println("ms-estadisticas recibió el evento de actualizacion del ranking: ");
    }


}
