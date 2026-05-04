package cl.duoc.ranking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ranking {
    private Integer id;
    private String regitroRanking;
    private String tipoRanking;
    private String rankings;

}
