package cl.duoc.ranking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RankingRequest {
    @NotBlank(message = "El nombre del Ranking no puede estar vacio")
    private String regitroRanking;
    private String tipoRanking;
    private String rankings;

}
