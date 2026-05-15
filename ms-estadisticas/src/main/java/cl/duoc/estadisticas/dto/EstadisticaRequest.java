package cl.duoc.estadisticas.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class EstadisticaRequest {
    private Integer idUsuario;
    private Integer idEquipo;
    private Integer idPartida;

    @PositiveOrZero(message = "Las victorias no pueden ser negativas")
    private Integer victorias;

    @PositiveOrZero(message = "Las derrotas no pueden ser negativas")
    private Integer derrotas;

    private Long duracionSegundos;
}