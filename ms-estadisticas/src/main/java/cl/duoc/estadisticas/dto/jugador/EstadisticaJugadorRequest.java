package cl.duoc.estadisticas.dto.jugador;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadisticaJugadorRequest {

    @NotNull(message = "El ID de usuario no puede ser nulo")
    private Integer idUsuario;

    @NotNull(message = "El número de victorias no puede ser nulo")
    @PositiveOrZero(message = "Las victorias no pueden ser negativas")
    private Integer victoriasJugador;

    @NotNull(message = "El número de derrotas no puede ser nulo")
    @PositiveOrZero(message = "Las derrotas no pueden ser negativas")
    private Integer derrotasJugador;
}