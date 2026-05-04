package cl.duoc.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RankingResponse {
    private Integer id;
    private String regitroRanking;
    private String tipoRanking;
    private String rankings;

}
