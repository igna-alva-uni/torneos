package cl.duoc.ranking.dto.tipoRanking;

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
public class TipoRankingResponse extends RepresentationModel<TipoRankingResponse> {
    private Integer idTipoRanking;
    private String nombreTipoRanking;
}
