package cl.duoc.ranking.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadisticaJugadorResponse {
    
    private Integer idEstadisticaJugador;
    private Integer idUsuario;
    private Integer victoriasJugador;
    private Integer derrotasJugador;

    private EstadisticaJugadorResponse estadisticas;
}