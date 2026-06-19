package cl.duoc.commons.event;

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
public class RankingUpdateEvent {
    private Integer tipoRanking;
    private Integer idEquipo;
    private Integer puntos;
}
