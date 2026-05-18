package cl.duoc.estadisticas.dto.partida;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadisticaPartidaResponse {
    private Integer idEstadisticaPartida;
    private Integer idPartida;
    private Duration duracion;
}