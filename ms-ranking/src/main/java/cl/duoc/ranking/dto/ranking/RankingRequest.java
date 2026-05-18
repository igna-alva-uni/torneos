package cl.duoc.ranking.dto.ranking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import cl.duoc.ranking.dto.registroRanking.RegistroRankingRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingRequest {

    @NotNull(message = "El ID del juego es obligatorio")
    private Integer idJuego;

    @NotNull(message = "El ID del tipo de ranking es obligatorio")
    private Integer idTipoRanking;

    @Valid
    private List<RegistroRankingRequest> registros;
}