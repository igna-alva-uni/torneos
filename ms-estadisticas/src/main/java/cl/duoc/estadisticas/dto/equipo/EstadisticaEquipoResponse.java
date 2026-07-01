package cl.duoc.estadisticas.dto.equipo;

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
public class EstadisticaEquipoResponse extends RepresentationModel<EstadisticaEquipoResponse> {
    private Integer idEstadisticaEquipo;
    private Integer idEquipo;
    private Integer victoriasEquipo;
    private Integer derrotasEquipo;
}
