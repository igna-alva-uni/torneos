package cl.duoc.ranking.dto.ranking;

import java.util.List;
import cl.duoc.ranking.dto.tipoRanking.TipoRankingResponse;
import cl.duoc.ranking.dto.registroRanking.RegistroRankingResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingResponse {
    private Integer idRanking;
    private Integer idJuego;
    
    // Relación limpia con el DTO correspondiente
    private TipoRankingResponse tipoRanking;
    
    // Lista de respuestas limpias sin la entidad JPA circular
    private List<RegistroRankingResponse> registros;
}