package cl.duoc.ranking.dto.registroRanking;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroRankingRequest {

    @NotNull(message = "El ID del equipo es obligatorio")
    private Integer idEquipo;

    @NotNull(message = "Los puntos son obligatorios")
    @Min(value = 0, message = "Los puntos no pueden ser un valor negativo")
    private Integer puntos;
}