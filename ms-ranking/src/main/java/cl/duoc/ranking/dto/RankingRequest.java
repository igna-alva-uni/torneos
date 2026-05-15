package cl.duoc.ranking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RankingRequest {

    @NotBlank(message = "El registro del ranking es obligatorio")
    @Size(max = 100, message = "El registro no puede superar los 100 caracteres")
    private String registroRanking;

    @NotBlank(message = "El tipo de ranking es obligatorio")
    private String tipoRanking;

    @NotBlank(message = "El detalle de los rankings no puede estar vacío")
    private String rankings;
}