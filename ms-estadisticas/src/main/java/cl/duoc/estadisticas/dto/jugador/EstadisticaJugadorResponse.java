package cl.duoc.estadisticas.dto.jugador;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class EstadisticaJugadorResponse extends RepresentationModel<EstadisticaJugadorResponse> {
    private Integer idEstadisticaJugador;
    private Integer idUsuario;
    private Integer victoriasJugador;
    private Integer derrotasJugador;
}
