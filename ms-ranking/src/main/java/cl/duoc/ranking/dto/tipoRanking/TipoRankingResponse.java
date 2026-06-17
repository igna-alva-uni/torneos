package cl.duoc.ranking.dto.tipoRanking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoRankingResponse {
    private Integer idTipoRanking;
    private String nombreTipoRanking;
}