package cl.duoc.ranking.dto.registroRanking;

import cl.duoc.commons.dto.EstadisticaEquipoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroRankingResponse {
    private Integer idRegistroRanking;
    private Integer idEquipo;
    private Integer puntos;
    private EstadisticaEquipoResponse estadisticas;
}