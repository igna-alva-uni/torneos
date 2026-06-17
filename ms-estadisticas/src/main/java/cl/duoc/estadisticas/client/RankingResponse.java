package cl.duoc.estadisticas.client;

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
}