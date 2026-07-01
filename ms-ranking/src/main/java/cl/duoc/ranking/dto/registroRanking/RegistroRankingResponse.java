package cl.duoc.ranking.dto.registroRanking;

import cl.duoc.commons.dto.EstadisticaEquipoResponse;
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
public class RegistroRankingResponse extends RepresentationModel<RegistroRankingResponse> {
    private Integer idRegistroRanking;
    private Integer idEquipo;
    private Integer puntos;
    private EstadisticaEquipoResponse estadisticas;
}
