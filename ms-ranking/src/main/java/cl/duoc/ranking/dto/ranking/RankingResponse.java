package cl.duoc.ranking.dto.ranking;

import java.util.List;
import cl.duoc.ranking.dto.tipoRanking.TipoRankingResponse;
import cl.duoc.ranking.dto.registroRanking.RegistroRankingResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class RankingResponse extends RepresentationModel<RankingResponse> {
    private Integer idRanking;
    private Integer idJuego;
    private TipoRankingResponse tipoRanking;
    private List<RegistroRankingResponse> registros;
}
