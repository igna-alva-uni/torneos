package cl.duoc.estadisticas.dto.partida;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadisticaPartidaRequest {

    @NotNull(message = "El ID de la partida no puede ser nulo")
    private Integer idPartida;

    @NotNull(message = "La duración no puede ser nula")
    private Duration duracion;
}