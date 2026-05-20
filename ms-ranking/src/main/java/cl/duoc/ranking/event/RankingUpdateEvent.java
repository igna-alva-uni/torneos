package cl.duoc.ranking.event;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RankingUpdateEvent implements RankingEvent {

    private Integer tipoRanking;
    private Integer idEquipo;
    private Integer puntos;

    @Override
    public Integer getTipoRanking() {
        return tipoRanking;
    }
}
