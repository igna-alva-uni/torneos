package cl.duoc.estadisticas.dto.equipo;

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
public class EstadisticaEquipoRequest {

    @NotNull(message = "El ID del equipo no puede ser nulo")
    private Integer idEquipo;

    @NotNull(message = "El número de victorias no puede ser nulo")
    @PositiveOrZero(message = "Las victorias no pueden ser negativas")
    private Integer victoriasEquipo;

    @NotNull(message = "El número de derrotas no puede ser nulo")
    @PositiveOrZero(message = "Las derrotas no pueden ser negativas")
    private Integer derrotasEquipo;
}