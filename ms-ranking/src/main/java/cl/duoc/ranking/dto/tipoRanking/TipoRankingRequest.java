package cl.duoc.ranking.dto.tipoRanking;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoRankingRequest {

    @NotBlank(message = "El tipo de ranking es obligatorio")
    @Size(max = 50, message = "El tipo de ranking no puede superar los 50 caracteres") // Lo bajé a 50 porque en tu JPA dice length = 50
    private String nombreTipoRanking;

}