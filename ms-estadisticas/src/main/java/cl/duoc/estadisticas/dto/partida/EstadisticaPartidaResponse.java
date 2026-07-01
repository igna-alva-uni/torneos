package cl.duoc.estadisticas.dto.partida;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class EstadisticaPartidaResponse extends RepresentationModel<EstadisticaPartidaResponse> {
    private Integer idEstadisticaPartida;
    private Integer idPartida;
    private Duration duracion;
}
